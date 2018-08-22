def buildPipeline() {

    def pipeline
    node {
        pipeline = new pipelineBuilder()
        pipeline.init()
    }
    return pipeline;
