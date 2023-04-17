C:\__apache-maven-3.8.4-for-insta\bin\mvn clean install -o  -DskipTests=true  -f ../eureka-server/pom.xml  && ^
docker compose stop eureka && ^
docker compose rm && ^
docker compose build eureka && ^
docker compose up -d eureka