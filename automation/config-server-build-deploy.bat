C:\__apache-maven-3.8.4-for-insta\bin\mvn clean install -DskipTests=true  -f ../config-server/pom.xml  && ^
docker compose stop config-server && ^
docker compose rm && ^
docker compose build config-server && ^
docker compose up config-server -d