def call(Map config) {
    def buildFilePath = "./build.gradle"
    if(config.containsKey('buildFilePath')){
      buildFilePath = config.get('buildFilePath')
    }
    pipeline {
      agent any
        stage ('Checkout') {
          steps {
            checkout scm
          }
        }
        stage ('Clean') {
          steps {
            sh "gradle -b ${buildFilePath} clean"
          }
        }
        stage ('Build') {
          steps {
            sh "gradle -b ${buildFilePath} build"
          }
        }
        
          stage ('Test') {
          when {
            expression {
              return ( config.containsKey('runTest') && config.get('runTest') )
            }
          }
          steps {
            sh "gradle -b ${buildFilePath} test"
          }
        }
      }
   }
