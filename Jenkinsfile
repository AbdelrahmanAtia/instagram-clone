pipeline {
    agent any
	
    environment {
        USER_PROJECT_DIR = 'user-info-service'
        POST_PROJECT_DIR = 'post-service'
    }
    
    stages {
        stage('Build') {
            steps {
                
                dir("${USER_PROJECT_DIR}") {
                    script {
                        bat 'mvn clean install -o -DskipTests=true'
                    }
                }
				
                dir("${POST_PROJECT_DIR}") {
                    script {
                        bat 'mvn clean install -o -DskipTests=true'
                    }
                }
            }
        }
        
        stage('Test') {
            steps {
                echo 'Starting the Test stage...'
                // Add test commands here, for example:
                echo 'Running tests...'
            }
        }
        
        stage('Deploy') {
            steps {
                echo 'Starting the Deploy stage...'
                // Add deploy commands here, for example:
                echo 'Deploying the application...'
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution finished..'
        }
    }
}
