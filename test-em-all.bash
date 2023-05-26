: ${HOST=localhost}
: ${PORT=8443}
: ${USER_UUID=1b9316e6-3e65-45fc-a958-9883445a9de8}
: ${POST_UUID_1=3005b434-d3ae-4d56-9b4c-04572980d45b}
: ${SKIP_CB_TESTS=false}
: ${SKIP_CB_TESTS=false}


function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
    echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
    echo  "- Failing command: $curlCmd"
    echo  "- Response Body: $RESPONSE"
    exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

function recreateUser() {
  
  echo recreating user with uuid $1 
  echo ================================================================
  local userUuid=$1
  local body=$2
  
  WRITER_ACCESS_TOKEN=$(curl -k https://writer:secret@$HOST:$PORT/oauth2/token -d grant_type=client_credentials -s | jq .access_token -r)
  AUTH="-H \"Authorization: Bearer $WRITER_ACCESS_TOKEN\""
  
  assertCurl 200 "curl -s -X DELETE $AUTH -k https://$HOST:$PORT/services/user-ms/users/?userUuid=${userUuid}"
  assertEqual 200 $(curl -X POST -s -k https://$HOST:$PORT/services/user-ms/users/ -H "Content-Type: application/json" -H "Authorization: Bearer $WRITER_ACCESS_TOKEN" --data "$body" -w "%{http_code}" -o /dev/null)
  
  echo ================================================================
}

function setupTestdata() {
  local userUuid="aeb7f3e9-3e66-4ad8-9bd4-15f2b6a09a7a"
  body="{\"username\": \"AbdelrahmanAttya\", \"email\": \"abc@gmail.com\", \"password\": \"123456\", \"userUuid\": \"$userUuid\"}"
  recreateUser "$userUuid" "$body"
}

function testCircuitBreaker() {
    echo  ------------------------------
    echo "Start Circuit Breaker tests!"
	echo  ------------------------------

    local userUuid="aeb7f3e9-3e66-4ad8-9bd4-15f2b6a09a7a"
    
	WRITER_ACCESS_TOKEN=$(curl -k https://writer:secret@$HOST:$PORT/oauth2/token -d grant_type=client_credentials -s | jq .access_token -r)
    AUTH="-H \"Authorization: Bearer $WRITER_ACCESS_TOKEN\""
    
	# First, use the health - endpoint to verify that the circuit breaker is closed
	echo ========================================================================
	echo use the health - endpoint to verify that the circuit breaker is closed
	echo ========================================================================
    assertEqual "CLOSED" "$(docker compose exec -T insta-ms-user-info curl -s http://insta-ms-user-info:8080/actuator/health | jq -r .components.circuitBreakers.details.postsCount.details.state)"
	
	echo ========================================================================
    echo Open the circuit breaker by running three slow calls in a row, i.e. that cause a timeout exception
    echo ========================================================================

    # Open the circuit breaker by running three slow calls in a row, i.e. that cause a timeout exception
    # Also, verify that we get 500 back and a timeout related error message
    for ((n=0; n<3; n++))
    do	
		assertCurl 500 "curl -s -X GET $AUTH -k 'https://$HOST:$PORT/services/user-ms/users/?userUuid=${userUuid}&delay=3'"
        message=$(echo $RESPONSE | jq -r .message)
        assertEqual "Did not observe any item or terminal signal within 2000ms" "${message:0:57}"
    done
	
	# Verify that the circuit breaker is open
    assertEqual "OPEN" "$(docker compose exec -T insta-ms-user-info curl -s http://insta-ms-user-info:8080/actuator/health | jq -r .components.circuitBreakers.details.postsCount.details.state)"

}

#########################################

echo ==============================
echo "printing general info"
echo ==============================
echo "HOST=${HOST}"
echo "PORT=${PORT}"

# print services regosterd in eureka
echo ==============================
echo services registered in eureka
echo ==============================
curl -H "accept:application/json" https://u:p@localhost:8443/eureka/api/apps -ks | jq -r .applications.application[].instance[].instanceId
echo =====================================


echo ==================================================================================
echo Verify access to Eureka and that all four microservices are registered in Eureka
echo ==================================================================================
# Verify access to Eureka and that all four microservices are registered in Eureka
assertCurl 200 "curl -H "accept:application/json" -k https://u:p@$HOST:$PORT/eureka/api/apps -s"
assertEqual 4 $(echo $RESPONSE | jq ".applications.application | length")
echo ==================================================================================


# Verify that the reader - client with only read scope can call the read API but not delete API.
echo ===============================================================================================
echo Verify that the reader - client with only read scope can call the read API but not delete API.
echo ===============================================================================================
READER_ACCESS_TOKEN=$(curl -k https://reader:secret@$HOST:$PORT/oauth2/token -d grant_type=client_credentials -s | jq .access_token -r)
#echo READER_ACCESS_TOKEN=$READER_ACCESS_TOKEN
READER_AUTH="-H \"Authorization: Bearer $READER_ACCESS_TOKEN\""
assertCurl 200 "curl $READER_AUTH -k https://$HOST:$PORT/services/post-ms/posts/count?userUuid=$USER_UUID -s"
assertCurl 403 "curl -X DELETE $READER_AUTH -k https://$HOST:$PORT/services/post-ms/posts/deleteByUuid?postUuid=$POST_UUID_1 -s"
echo ===============================================================================================


# Verify access to the Config server and that its encrypt/decrypt endpoints work
echo ===============================================================================================
echo Verify access to the Config server and that its encrypt/decrypt endpoints work
echo ===============================================================================================
assertCurl 200 "curl -H "accept:application/json" -k https://dev-usr:dev-pwd@$HOST:$PORT/config/post-ms/docker -s"
TEST_VALUE="hello world"
ENCRYPTED_VALUE=$(curl -k https://dev-usr:dev-pwd@$HOST:$PORT/config/encrypt --data-urlencode "$TEST_VALUE" -s)
DECRYPTED_VALUE=$(curl -k https://dev-usr:dev-pwd@$HOST:$PORT/config/decrypt -d $ENCRYPTED_VALUE -s)
assertEqual "$TEST_VALUE" "$DECRYPTED_VALUE"
echo ===============================================================================================


setupTestdata
if [[ $SKIP_CB_TESTS == "false" ]]
then
    testCircuitBreaker
fi
