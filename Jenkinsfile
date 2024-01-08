pipeline {
    agent any
    stages {
        stage('chekout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'Pat_jenkins', url: 'https://github.com/rebirth2001/QUIZLY.git']])
                
            }
        }
    
 stage('Build and Test Microservices') {
            steps {
                script {
                    // Liste des microservices à traiter
                    def microservices = ['quizs', 'users', 'jwt']

                    // Itération sur chaque microservice
                    for (def microservice in microservices) {
                        echo "Building and testing ${microservice}"
                        
                        // Exécutez le Jenkinsfile du microservice
                        build job: "${microservice}/Jenkinsfile", wait: true
                    }
                }
            }
        }
    }
}
