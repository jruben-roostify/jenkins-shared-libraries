def call(Map config) {
    buildFilePath = "./build.gradle"
    if(config.containsKey('buildFilePath')){
      buildFilePath = config.get('buildFilePath')
    }
    pipeline {
      agent any
       stages {
        stage ('Checkout') {
          steps {
            checkout scm
          }
        }
        stage ('Clean') {
          steps {
              sh "./gradlew -b ${buildFilePath} clean"
          }
        }
        stage ('Build') {
          steps {
            //sh "./gradlew -b ${buildFilePath} build"
            gradle {
                tasks('build')
                switches('--info')
            }
          }
        }
        
          stage ('Test') {
          when {
            expression {
              return ( config.containsKey('runTest') && config.get('runTest') )
            }
          }
          steps {
            sh "./gradlew -b ${buildFilePath} test"
          }
        }
      }
    }
}
