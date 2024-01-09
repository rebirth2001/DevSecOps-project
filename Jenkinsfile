pipeline {
    agent any
    stages {
        stage('chekout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'Pat_jenkins', url: 'https://github.com/rebirth2001/QUIZLY.git']])
                
            }
        }
    
 stages {
        stage('Build Users Microservice') {
            steps {
                dir('users') {
                    sh 'mvn clean install'
                }
            }
        }
        
        stage('Build Quizs Microservice') {
            steps {
                dir('quizs') {
                    sh 'mvn clean install'
                }
            }
        }
        
        stage('Build JWT Microservice') {
            steps {
                dir('jwt') {
                    sh 'mvn clean install'
                }
            }
        }
    }
}
}
        
    
