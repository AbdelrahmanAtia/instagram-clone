: ${HOST=localhost}
: ${PORT=8443}

echo "HOST=${HOST}"
echo "PORT=${PORT}"

ACCESS_TOKEN=$(curl -k https://writer:secret@$HOST:$PORT/oauth2/token -d grant_type=client_credentials -s | jq .access_token -r)
echo ACCESS_TOKEN=$ACCESS_TOKEN


