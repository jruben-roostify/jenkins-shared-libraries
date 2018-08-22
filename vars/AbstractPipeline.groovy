
def buildPipeline() {

    def pipeline
    node {
        pipeline = new JenkinsPipelineBuilder()
        pipeline.init()
    }
    return pipeline;
}
