@echo off
REM Build the Docker image
docker build -t my-jenkins-image .

REM Check if a container named my-jenkins is already running and stop it
docker ps -a --format "{{.Names}}" | findstr /i "my-jenkins" >nul
IF %ERRORLEVEL% EQU 0 (
    echo Stopping and removing existing my-jenkins container...
    docker stop my-jenkins
    docker rm my-jenkins
)

REM Run the Docker container
docker run -d -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home --name my-jenkins my-jenkins-image

REM Confirm the container is running
docker ps --filter "name=my-jenkins"
