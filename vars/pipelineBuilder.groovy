void init() {
    echo 'Initializing PipelineSteps.'
}

void gradleSteps(){
  try{
        stage ('Checkout') {
              steps {
                checkout scm
              }
        }
        stage ('Clean') {
            steps {
                sh "./gradlew clean"
            }
        }
        stage ('Build') {
          steps {
            sh "./gradlew build"
          }
        }
        stage ('Test') {
          steps {
            sh "./gradlew test"
          }
        }
  }
  catch (e) {
    currentBuild.result = "FAILED"
    throw e
  }
}

void sonarStep(){
    stage ('Sonar'){
        step {
            script {
                if (env.GIT_BRANCH == 'develop') {
                    sh "./gradlew sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_PASSWORD}"
                }else if (env.CHANGE_ID){
                    echo 'This is a PR build. Running sonnar preview analysis'
                    sh "./gradlew sonar -Dsonar.github.pullRequest=${env.CHANGE_ID} -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_PASSWORD} -Dsonar.analysis.mode=preview -Dsonar.github.oauth=${GITHUB_OAUTH_TOKEN} -Dsonar.github.repository=${env.CUSTOM_SONAR_REPO_NAME} -i"
                }else{
                    echo 'This is a branch build.'
                    sh "./gradlew sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_PASSWORD} -Dsonar.github.repository=${env.CUSTOM_SONAR_REPO_NAME} -Dsonar.branch=${env.GIT_BRANCH} -i"
                }
            }
        }
    }
}
