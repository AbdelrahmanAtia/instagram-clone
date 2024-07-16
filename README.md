


# instagram-clone

#  Cloning the project

1- Go to c drive <br>

2- open cmd or PowerShell in c drive and run the following commands  
```bash
   mkdir _workspaces  
   cd C:\_workspaces 
   -- replace {PAT} with your actual personal access token
   git clone https://{PAT}@github.com/AbdelrahmanAtia/instagram-clone.git
```
3- import the project into Github Desktop <br>

   
# Deploying all services to the cluster
1- move to instagram-clone/automation directory <br>
2- execute batch file *all-services-build-deploy.bat* <br>

# Running test script
1- install soup-ui tool..u can download it from here https://www.soapui.org/downloads/soapui/ <br>
2- import the following file instagram-clone/insta-clone-testing-soapui-project.xml into soup ui <br>
3- run the whole test suit <br>


# Software needed to be installed
1- Github Desktop <br>
2- Docker <br>
3- Eclipse <br>
4- soup ui <br>
5- visual studio code (VSC) <br>

# Tools used
<table>
     <thead>
      <tr>
        <th></th>
        <th></th>
      </tr>
    </thead>
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
</table>
<br>



