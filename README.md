

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
then run it and make sure that all tests are passed. <br>

**Tools used**  <br>
<table>
    
    <tbody>
        <tr>
            <td>Discovery server</td>
            <td>Netflix Eureka</td>
        </tr>
        <tr>
            <td>Edge server</td>
            <td>Spring Cloud Gateway and Spring Security OAuth</td>
        </tr>
        <tr>
            <td>Centralized configuration</td>
            <td>Spring Cloud Configuration Server</td>
        </tr>

    </tbody>
  </table><br>



**Properties description**  <br>
<table>
    <thead>
      <tr>
        <th>Property name</th>
        <th>Description</th>
      </tr>
    </thead>
    <tbody>
        <tr>
            <td>spring.application.name</td>
            <td>it represents the name under which the application will be registered in eureka also
                it represents the externalized config file name of that microservice in the config-repo folder
            </td>
        </tr>
    </tbody>
  </table>
