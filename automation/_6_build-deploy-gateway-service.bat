C:\__apache-maven-3.8.4-for-insta\bin\mvn clean install -o  -DskipTests=true  -f ../gateway/pom.xml  & ^
docker compose stop gateway & ^
docker compose rm & ^
docker compose build gateway & ^
docker compose up -d gateway