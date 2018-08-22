void init() {
    echo 'Initializing PipelineSteps.'
}

void gradleSteps(){
  try{
  
  }
  catch (e) {
            currentBuild.result = "FAILED"
            throw e
  }

}
