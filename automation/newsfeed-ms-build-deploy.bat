C:\__apache-maven-3.8.4-for-insta\bin\mvn clean install -o  -DskipTests=true  -f ../newsfeed/pom.xml  && ^
docker compose stop insta-ms-newsfeed && ^
docker compose rm && ^
docker compose build insta-ms-newsfeed && ^
docker compose up -d insta-ms-newsfeed