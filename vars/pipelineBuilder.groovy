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
