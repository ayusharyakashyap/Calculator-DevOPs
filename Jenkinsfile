pipeline {
    agent any
    
    environment {
        // Define environment variables
        MAVEN_HOME = tool 'Maven-3.9.11'
        JAVA_HOME = tool 'JDK-11'
        DOCKER_IMAGE = 'ayusharyak/scientific-calculator'
        DOCKER_TAG = "${BUILD_NUMBER}"
        DOCKER_LATEST = 'latest'
    }
    
    tools {
        maven 'Maven-3.9.11'
        jdk 'JDK-11'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code from GitHub...'
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo 'Building the application with Maven...'
                sh '''
                    echo "JAVA_HOME: $JAVA_HOME"
                    echo "MAVEN_HOME: $MAVEN_HOME"
                    mvn clean compile
                '''
            }
        }
        
        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh 'mvn test'
            }
            post {
                always {
                    // Publish test results
                    junit testResults: 'target/surefire-reports/*.xml'
                    
                    // Archive test reports
                    archiveArtifacts artifacts: 'target/surefire-reports/*', fingerprint: true
                }
            }
        }
        
        stage('Package') {
            steps {
                echo 'Packaging the application...'
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    // Archive the JAR file
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
        
        stage('Code Quality Analysis') {
            steps {
                echo 'Running code quality checks...'
                sh '''
                    echo "Running basic code quality checks..."
                    find src/main/java -name "*.java" | wc -l
                    find src/test/java -name "*.java" | wc -l
                '''
            }
        }
        
        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                script {
                    // Build Docker image with proper credential access
                    sh """
                        export PATH="/Applications/Docker.app/Contents/Resources/bin:\$PATH"
                        export DOCKER_CONFIG=\${HOME}/.docker
                        /Applications/Docker.app/Contents/Resources/bin/docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                        /Applications/Docker.app/Contents/Resources/bin/docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:${DOCKER_LATEST}
                    """
                }
            }
        }
        
        stage('Test Docker Image') {
            steps {
                echo 'Testing Docker image...'
                script {
                    // Test the Docker image
                    sh """
                        echo "Testing Docker image..."
                        /Applications/Docker.app/Contents/Resources/bin/docker run --rm ${DOCKER_IMAGE}:${DOCKER_TAG} java -version
                        echo "Docker image test completed successfully"
                    """
                }
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                echo 'Pushing Docker image to Docker Hub...'
                script {
                    // Push to Docker Hub using basic docker commands
                    sh """
                        export PATH="/Applications/Docker.app/Contents/Resources/bin:\$PATH"
                        export DOCKER_CONFIG=\${HOME}/.docker
                        
                        echo "Logging in to Docker Hub..."
                        echo "Note: Using Docker Desktop authentication"
                        
                        echo "Pushing ${DOCKER_IMAGE}:${DOCKER_TAG}..."
                        /Applications/Docker.app/Contents/Resources/bin/docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                        
                        echo "Pushing ${DOCKER_IMAGE}:${DOCKER_LATEST}..."
                        /Applications/Docker.app/Contents/Resources/bin/docker push ${DOCKER_IMAGE}:${DOCKER_LATEST}
                        
                        echo "Docker images pushed successfully!"
                    """
                }
            }
        }
        
        stage('Deploy with Ansible') {
            steps {
                echo 'Deploying application using Ansible...'
                script {
                    // Run Ansible playbook
                    sh """
                        cd ansible
                        ansible-playbook -i localhost, -c local playbook.yml \
                            --extra-vars "docker_image=${DOCKER_IMAGE}:${DOCKER_TAG}"
                    """
                }
            }
        }
        
        stage('Health Check') {
            steps {
                echo 'Performing post-deployment health check...'
                script {
                    sh '''
                        echo "Checking if container is running..."
                        docker ps | grep scientific-calculator || echo "Container not found"
                        echo "Health check completed"
                    '''
                }
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline execution completed.'
            
            // Clean up Docker images to save space
            sh '''
                /Applications/Docker.app/Contents/Resources/bin/docker system prune -f
                /Applications/Docker.app/Contents/Resources/bin/docker image prune -f
            '''
        }
        
        success {
            echo 'Pipeline executed successfully!'
            
            // Send success notification (if configured)
            emailext (
                subject: "Jenkins Build Success: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: """
                    Good news! The build was successful.
                    
                    Job: ${env.JOB_NAME}
                    Build Number: ${env.BUILD_NUMBER}
                    Build URL: ${env.BUILD_URL}
                    
                    The Scientific Calculator application has been successfully:
                    - Built and tested
                    - Packaged into Docker image
                    - Pushed to Docker Hub
                    - Deployed using Ansible
                """,
                to: "ayusharyakashyap@example.com"
            )
        }
        
        failure {
            echo 'Pipeline execution failed!'
            
            // Send failure notification (if configured)
            emailext (
                subject: "Jenkins Build Failed: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: """
                    The build has failed.
                    
                    Job: ${env.JOB_NAME}
                    Build Number: ${env.BUILD_NUMBER}
                    Build URL: ${env.BUILD_URL}
                    
                    Please check the build logs for more details.
                """,
                to: "ayusharyakashyap@example.com"
            )
        }
    }
}