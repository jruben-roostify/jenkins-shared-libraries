def call(Map config) {
 pipeline {
  agent any
  stage('Checkout') {
   steps {
    checkout scm
   }
  }
  stage('Clean') {
   steps {
    sh "gradle clean"
   }
  }
  stage('Build') {
   steps {
    sh "gradle build"
   }
  }

  stage('Test') {
   when {
    expression {
     return (config.containsKey('runTest') && config.get('runTest'))
    }
   }
   steps {
    sh "gradle test"
   }
  }
 }
}
