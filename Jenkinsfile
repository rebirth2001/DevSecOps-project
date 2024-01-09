pipeline {
    agent any
    stages {
        stage('chekout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'Pat_jenkins', url: 'https://github.com/rebirth2001/QUIZLY.git']])
                
            }
        }
    stage('Build Quizs Microservice') {
            steps {
                dir('eureka') {
                    sh 'mvn clean install'
                    sh 'docker build -t joui5/app .'
                    withCredentials([string(credentialsId: 'docker-hub-password', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u="joui5" -p="$dockerhubpwd"'

                        sh 'docker push joui5/app' 
}
                }
            }
        }
    
        
        }
    }


        
    
