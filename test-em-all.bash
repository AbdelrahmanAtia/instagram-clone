: ${HOST=localhost}
: ${PORT=8443}
: ${USER_UUID=1b9316e6-3e65-45fc-a958-9883445a9de8}
: ${POST_UUID_1=3005b434-d3ae-4d56-9b4c-04572980d45b}


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

echo "HOST=${HOST}"
echo "PORT=${PORT}"

ACCESS_TOKEN=$(curl -k https://writer:secret@$HOST:$PORT/oauth2/token -d grant_type=client_credentials -s | jq .access_token -r)
echo ACCESS_TOKEN=$ACCESS_TOKEN


# Verify that the reader - client with only read scope can call the read API but not delete API.
READER_ACCESS_TOKEN=$(curl -k https://reader:secret@$HOST:$PORT/oauth2/token -d grant_type=client_credentials -s | jq .access_token -r)
echo READER_ACCESS_TOKEN=$READER_ACCESS_TOKEN
READER_AUTH="-H \"Authorization: Bearer $READER_ACCESS_TOKEN\""

assertCurl 200 "curl $READER_AUTH -k https://$HOST:$PORT/services/posts/count?userUuid=$USER_UUID -s"

assertCurl 403 "curl -X DELETE $READER_AUTH -k https://$HOST:$PORT/services/posts/deleteByUuid?postUuid=$POST_UUID_1 -s"


