

# instagram-clone

**Deploying all services to the cluster** <br>
1- move to instagram-clone/automation directory <br>
2- execute batch file *_3_build-deploy-all.bat* <br>

**Running test script** <br>
1- move to instagram-clone dir <br>
2- right click on and click "Git Bash Here <br>
3- run command:  `bash test-em-all.bash` <br>


**Software needed to be installed** <br>
1- Docker <br>
2- jq tool  <br>


**jq tool installation steps**  <br>
1 - open git bash and run command `curl -L -o /usr/bin/jq.exe https://github.com/stedolan/jq/releases/latest/download/jq-win64.exe` <br>
2 - Make sure jq is installed with the `jq --version` command.   <br>


**E2E testing using postman**  <br>
to run e2e tests, import the following file into postman: instagram-clone.postman_collection.json <br>
then run it and make sure that all tests are passed.
