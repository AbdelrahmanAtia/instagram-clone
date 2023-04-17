C:\__apache-maven-3.8.4-for-insta\bin\mvn clean install -o  -DskipTests=true  -f ../post-service/pom.xml  && ^
docker compose stop insta-ms-post && ^
docker compose rm && ^
docker compose build insta-ms-post && ^
docker compose up -d insta-ms-post