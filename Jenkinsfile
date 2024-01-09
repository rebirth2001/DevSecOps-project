pipeline {
    agent any
    stages {
        stage('chekout') {
            steps {
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'Pat_jenkins', url: 'https://github.com/rebirth2001/QUIZLY.git']])
                
            }
        }
    stage('working in eureka') {
            steps {
                dir('eureka') {
                    sh 'mvn clean install'
                    sh 'docker build -t joui5/eureka .'
                    withCredentials([string(credentialsId: 'docker-hub-password', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u="joui5" -p="$dockerhubpwd"'

                        sh 'docker push joui5/eureka' 
                            }
                }
            }
        }
    stage('working in jwt') {
            steps {
                dir('eureka') {
                    sh 'mvn clean install'
                    sh 'docker build -t joui5/jwt .'
                    withCredentials([string(credentialsId: 'docker-hub-password', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u="joui5" -p="$dockerhubpwd"'

                        sh 'docker push joui5/jwt' 
                            }
                }
            }
        }
        
        }
    }


        
    
