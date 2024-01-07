pipeline {
    agent any
    tools {
        maven 'Maven 3.9.6'
    }
    parameters {
        string(name: 'Name', defaultValue:'', description: 'What is your name?')
        choice(name: 'VERSION', choices:['1.0','2.0','3.0'])
        booleanParam(name: 'runTests', defaultValue: true, description: 'Do you want to run Tests?')
    }
    stages {
        stage("Build") {
            steps {
                echo "Building..."
                sh 'cd '
                sh 'mvn package'
            }
        }
        stage("Test") {
            when {
                expression {
                    params.runTests
                }
            }
            steps {
                echo "Testing..."
            }
        }
        stage("Deploy") {
            steps {
                echo "Deploying..."
            }
        }
    }
}
