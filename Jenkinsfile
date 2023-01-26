pipeline {
    //agent { label 'agent1' }
    agent any

    tools {
        // Run the Maven installation named "3.8.7" and add it to the path.
        maven "3.8.7"
    }

    stages {
        stage('clean and checkout') {
            steps {
                sh 'mvn clean -f ./backend/'
                echo 'downloading github project...'
                git credentialsId: 'FrodeToken', url: 'https://github.com/frobo97/proj-assi-2-starting-point-private-frode.git', branch: 'master'
            }
        }

        stage('build') {
            steps {
                echo 'building...'
                sh 'mvn test-compile -f ./backend/'
                echo 'finished building'
            }
        }

        stage('test') {
            steps {
                echo 'starting test.....'
                sh 'mvn surefire:test -f ./backend/'
                echo 'finished test'
            }
        }

        stage('package') {
            steps {
                echo 'packaging...'
                sh 'mvn war:war -f ./backend/'
                echo 'packaged'
            }
        }
    }

    post {
        always {
            echo 'generating test report....'
            junit 'backend/target/*reports/**/*.xml'
            echo 'test report generated'
        }
    }
}
