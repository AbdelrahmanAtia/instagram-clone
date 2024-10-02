

@echo off
REM Check if the network exists
docker network ls | findstr /r "\bjenkins\b" >nul
IF ERRORLEVEL 1 (
    REM Create the network if it doesn't exist
    docker network create jenkins
    echo Network 'jenkins' created.
) ELSE (
    echo Network 'jenkins' already exists.
)


docker run --name jenkins-docker --rm --detach ^
  --privileged --network jenkins --network-alias docker ^
  --env DOCKER_TLS_CERTDIR=/certs ^
  --volume jenkins-docker-certs:/certs/client ^
  --volume jenkins-data:/var/jenkins_home ^
  --publish 2376:2376 ^
  docker:dind


docker build -t myjenkins-blueocean:2.462.1-1 .


docker run --name jenkins-blueocean --restart=on-failure --detach ^
  --network jenkins --env DOCKER_HOST=tcp://docker:2376 ^
  --env DOCKER_CERT_PATH=/certs/client --env DOCKER_TLS_VERIFY=1 ^
  --volume jenkins-data:/var/jenkins_home ^
  --volume jenkins-docker-certs:/certs/client:ro ^
  --publish 8080:8080 --publish 50000:50000 myjenkins-blueocean:2.462.1-1