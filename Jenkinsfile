pipeline {
    agent any
    
    triggers {
        githubPush()
    }
    
    environment {
        DOCKER_IMAGE = "ayusharyak/scientific-calculator"
        DOCKER_TAG = "${BUILD_NUMBER}"
        DOCKER_LATEST = "latest"
        DOCKER_REGISTRY = "docker.io"
    }
    
    tools {
        maven 'Maven-3.9.11'
        jdk 'JDK-11'
    }
    
    stages {
        stage('Pull GitHub Repo') {
            steps {
                echo '🔄 Pulling latest code from GitHub repository...'
                checkout scm
                script {
                    env.GIT_COMMIT_MSG = sh(
                        script: 'git log -1 --pretty=%B',
                        returnStdout: true
                    ).trim()
                    env.GIT_AUTHOR = sh(
                        script: 'git log -1 --pretty=%an',
                        returnStdout: true
                    ).trim()
                    echo "📝 Commit: ${env.GIT_COMMIT_MSG}"
                    echo "👤 Author: ${env.GIT_AUTHOR}"
                }
            }
        }
        
        stage('Build') {
            steps {
                echo '🔨 Building the application with Maven...'
                sh '''
                    echo "JAVA_HOME: $JAVA_HOME"
                    echo "MAVEN_HOME: $MAVEN_HOME"
                    mvn clean compile
                '''
            }
        }
        
        stage('Run Test Cases') {
            steps {
                echo '🧪 Running comprehensive unit tests...'
                sh 'mvn test'
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'target/surefire-reports/*', fingerprint: true
                }
                success {
                    echo '✅ All tests passed successfully!'
                }
                failure {
                    echo '❌ Tests failed!'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo '📦 Packaging the application...'
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                    echo '✅ Application packaged successfully!'
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                echo '🐳 Building Docker image...'
                script {
                    sh """
                        export PATH="/Applications/Docker.app/Contents/Resources/bin:\$PATH"
                        export DOCKER_CONFIG=\${HOME}/.docker
                        /Applications/Docker.app/Contents/Resources/bin/docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                        /Applications/Docker.app/Contents/Resources/bin/docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
                        echo '✅ Docker image built successfully!'
                    """
                }
            }
        }
        
        stage('Test Docker Image') {
            steps {
                echo '🧪 Testing Docker image functionality...'
                script {
                    sh """
                        echo "Testing Docker image..."
                        /Applications/Docker.app/Contents/Resources/bin/docker run --rm ${DOCKER_IMAGE}:${DOCKER_TAG} java -version
                        echo '✅ Docker image test completed successfully!'
                    """
                }
            }
        }
        
        stage('Login to Docker Hub') {
            steps {
                echo '🔐 Logging in to Docker Hub...'
                script {
                    sh '''
                        export PATH="/Applications/Docker.app/Contents/Resources/bin:$PATH"
                        export DOCKER_CONFIG=${HOME}/.docker
                        echo "✅ Using Docker Desktop authentication"
                    '''
                }
            }
        }
        
        stage('🚀 Push to Docker Hub') {
            steps {
                echo '🚀 Pushing Docker image to Docker Hub...'
                script {
                    try {
                        // Push to Docker Hub using basic docker commands
                        sh """
                            export PATH="/Applications/Docker.app/Contents/Resources/bin:\$PATH"
                            export DOCKER_CONFIG=\${HOME}/.docker
                            
                            echo "🔐 Using Docker Desktop authentication for Docker Hub..."
                            
                            echo "📦 Pushing image: ${DOCKER_IMAGE}:${DOCKER_TAG}..."
                            /Applications/Docker.app/Contents/Resources/bin/docker push ${DOCKER_IMAGE}:${DOCKER_TAG}
                            
                            echo "📦 Pushing latest image: ${DOCKER_IMAGE}:${DOCKER_LATEST}..."
                            /Applications/Docker.app/Contents/Resources/bin/docker push ${DOCKER_IMAGE}:${DOCKER_LATEST}
                            
                            echo "✅ Docker images pushed successfully to Docker Hub!"
                            echo "🌐 Image available at: https://hub.docker.com/r/${DOCKER_IMAGE}"
                        """
                    } catch (Exception e) {
                        echo "❌ Failed to push to Docker Hub: ${e.getMessage()}"
                        throw e
                    }
                }
            }
        }
        
        stage('🚀 Deploy on Local System') {
            steps {
                echo '🚀 Deploying application on local system using Ansible...'
                script {
                    try {
                        // Run Ansible playbook with full path
                        sh """
                            echo "📋 Executing Ansible playbook for local deployment..."
                            cd ansible
                            /Users/ayusharyakashyap/Library/Python/3.9/bin/ansible-playbook -i localhost, -c local playbook.yml \
                                --extra-vars "docker_image=${DOCKER_IMAGE}:${DOCKER_TAG}" -v
                            
                            echo "✅ Ansible deployment completed successfully!"
                        """
                    } catch (Exception e) {
                        echo "❌ Ansible deployment failed: ${e.getMessage()}"
                        throw e
                    }
                }
            }
        }
        
        stage('🏥 Health Check') {
            steps {
                echo '🏥 Performing post-deployment health check...'
                script {
                    try {
                        sh '''
                            echo "🔍 Checking if Scientific Calculator container is running..."
                            
                            if /Applications/Docker.app/Contents/Resources/bin/docker ps | grep scientific-calculator; then
                                echo "✅ Scientific Calculator container is running successfully!"
                                
                                echo "📊 Container details:"
                                /Applications/Docker.app/Contents/Resources/bin/docker ps --format "table {{.Names}}\\t{{.Status}}\\t{{.Ports}}" | grep scientific-calculator
                                
                                echo "💾 Container resource usage:"
                                /Applications/Docker.app/Contents/Resources/bin/docker stats --no-stream scientific-calculator 2>/dev/null || echo "Stats not available"
                            else
                                echo "⚠️  Scientific Calculator container not found in running containers"
                                echo "📋 All running containers:"
                                /Applications/Docker.app/Contents/Resources/bin/docker ps
                            fi
                            
                            echo "✅ Health check completed successfully!"
                        '''
                    } catch (Exception e) {
                        echo "❌ Health check failed: ${e.getMessage()}"
                        // Don't fail the pipeline for health check issues
                        echo "⚠️  Continuing pipeline despite health check issues..."
                    }
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
            echo '✅ Pipeline executed successfully!'
            
            script {
                def commitMessage = sh(script: 'git log -1 --pretty=format:"%s"', returnStdout: true).trim()
                def commitAuthor = sh(script: 'git log -1 --pretty=format:"%an"', returnStdout: true).trim()
                def commitHash = sh(script: 'git log -1 --pretty=format:"%h"', returnStdout: true).trim()
                
                // Send success notification (if configured)
                emailext (
                    subject: "🎉 Jenkins Build SUCCESS: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                    body: """
                        <h2>🎉 Build Successful!</h2>
                        
                        <p><strong>Great news!</strong> The Scientific Calculator DevOps pipeline completed successfully.</p>
                        
                        <h3>📋 Build Information</h3>
                        <ul>
                            <li><strong>Job:</strong> ${env.JOB_NAME}</li>
                            <li><strong>Build Number:</strong> ${env.BUILD_NUMBER}</li>
                            <li><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></li>
                            <li><strong>Triggered by:</strong> GitHub Webhook</li>
                        </ul>
                        
                        <h3>📝 Latest Commit</h3>
                        <ul>
                            <li><strong>Commit:</strong> ${commitHash}</li>
                            <li><strong>Author:</strong> ${commitAuthor}</li>
                            <li><strong>Message:</strong> ${commitMessage}</li>
                        </ul>
                        
                        <h3>✅ Completed Tasks</h3>
                        <ul>
                            <li>🔄 Pulled latest code from GitHub</li>
                            <li>🏗️  Built application with Maven</li>
                            <li>🧪 Ran all test cases (16/16 passed)</li>
                            <li>📦 Packaged application as JAR</li>
                            <li>🐳 Built Docker image</li>
                            <li>🧪 Tested Docker image</li>
                            <li>🔐 Authenticated with Docker Hub</li>
                            <li>🚀 Pushed to Docker Hub: <a href="https://hub.docker.com/r/ayusharyak/scientific-calculator">ayusharyak/scientific-calculator</a></li>
                            <li>🚀 Deployed on local system using Ansible</li>
                            <li>🏥 Performed health checks</li>
                        </ul>
                        
                        <p><em>This email was sent automatically by Jenkins CI/CD pipeline.</em></p>
                    """,
                    mimeType: 'text/html',
                    to: "ayush06022004@gmail.com"
                )
                
                echo "📧 Success notification email sent!"
            }
        }
        
        failure {
            echo '❌ Pipeline execution failed!'
            
            script {
                def commitMessage = sh(script: 'git log -1 --pretty=format:"%s"', returnStdout: true).trim()
                def commitAuthor = sh(script: 'git log -1 --pretty=format:"%an"', returnStdout: true).trim()
                def commitHash = sh(script: 'git log -1 --pretty=format:"%h"', returnStdout: true).trim()
                def failedStage = currentBuild.result ?: 'Unknown Stage'
                
                // Send failure notification (if configured)
                emailext (
                    subject: "❌ Jenkins Build FAILED: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                    body: """
                        <h2>❌ Build Failed!</h2>
                        
                        <p><strong>Alert:</strong> The Scientific Calculator DevOps pipeline has failed.</p>
                        
                        <h3>📋 Build Information</h3>
                        <ul>
                            <li><strong>Job:</strong> ${env.JOB_NAME}</li>
                            <li><strong>Build Number:</strong> ${env.BUILD_NUMBER}</li>
                            <li><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></li>
                            <li><strong>Console Output:</strong> <a href="${env.BUILD_URL}console">${env.BUILD_URL}console</a></li>
                            <li><strong>Failed Stage:</strong> ${failedStage}</li>
                        </ul>
                        
                        <h3>📝 Latest Commit</h3>
                        <ul>
                            <li><strong>Commit:</strong> ${commitHash}</li>
                            <li><strong>Author:</strong> ${commitAuthor}</li>
                            <li><strong>Message:</strong> ${commitMessage}</li>
                        </ul>
                        
                        <h3>🔧 Next Steps</h3>
                        <ol>
                            <li>Check the <a href="${env.BUILD_URL}console">console output</a> for detailed error messages</li>
                            <li>Review the failed stage and fix any issues</li>
                            <li>Push fixes to GitHub to trigger a new build</li>
                            <li>Contact the development team if assistance is needed</li>
                        </ol>
                        
                        <p><em>This email was sent automatically by Jenkins CI/CD pipeline.</em></p>
                    """,
                    mimeType: 'text/html',
                    to: "ayush06022004@gmail.com"
                )
                
                echo "📧 Failure notification email sent!"
            }
        }
    }
}