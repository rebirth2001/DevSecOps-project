pipeline {
    agent any
    stages {
        stage('chekout') {
            steps {
                // generated by 
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[credentialsId: 'Pat_jenkins', url: 'https://github.com/rebirth2001/QUIZLY.git']])
                
            }
        }
    
    stage('Build ') {
        steps {
            script {
                def microservice = "eureka"
                def jenkinsfile = "Jenkinsfile"  // assuming the Jenkinsfile is named "Jenkinsfile"

                echo "Building ${microservice}"
                sh "pwd"
                sh"ls ${microservice}"
                sh "ls"
                build job: "${microservice}/${jenkinsfile}"
                
                
            }
        }
    }
 }}
