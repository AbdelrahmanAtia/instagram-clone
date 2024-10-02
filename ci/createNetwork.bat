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