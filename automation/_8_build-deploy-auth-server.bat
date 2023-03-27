C:\__apache-maven-3.8.4-for-insta\bin\mvn clean install -DskipTests=true  -f ../authorization-server/pom.xml  & ^
docker compose stop auth-server & ^
docker compose rm & ^
docker compose build auth-server & ^
docker compose up -d auth-server