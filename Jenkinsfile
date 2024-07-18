pipeline {
    agent any
    
    stages {
        stage('Build') {
            steps {
                echo 'Starting the Build stage...'
                // Add build commands here, for example:
                echo 'Building the application...'
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
