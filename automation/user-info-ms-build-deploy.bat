C:\__apache-maven-3.8.4-for-insta\bin\mvn clean install  -DskipTests=true  -f ../user-info-service/pom.xml && ^
docker compose stop insta-ms-user-info && ^
docker compose rm -f && ^
docker compose build insta-ms-user-info && ^
docker compose up -d insta-ms-user-info