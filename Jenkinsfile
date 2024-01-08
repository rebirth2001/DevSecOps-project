pipeline {
    agent any
    stages {
        stage('chekout') {
            steps {
                // generated by 
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'Pat_jenkins', url: 'https://github.com/rebirth2001/QUIZLY.git']])
                
            }
        }
    
 stage('Build and Test Microservices') {
            steps {
                script {
                    // Spécifiez le dossier du microservice
                    def microserviceFolder = 'eureka'  // Assurez-vous que le nom est correct

                    // Exécutez le Jenkinsfile du microservice
                    echo "Building and testing ${microserviceFolder}"
                    dir(microserviceFolder) {
                        script {
                            echo "Current directory: ${pwd()}"
                            echo "Maven version: "
                            sh 'mvn --version'
                            
                            echo "Building the project: "
                            sh 'mvn clean install'
                        }
                    }
                }
            }
        }
