void initialize() {
    echo 'Initializing sonarAnalysis.'
}

def getRepoName(){
    def repo = "${env.GIT_URL}"
    repo_val = repo.replaceAll('https://github.com/', '').replaceAll('.git', '')
    return repo_val;
}

void sonarAnalysis(String buildFileLocation, Map config){
    script{
        if (env.GIT_BRANCH == 'develop') {
        println 'Performing full develop branch Analysis';
        sh "./gradlew -b "+buildFileLocation+" sonar -Dsonar.host.url='${SONAR_URL}' -Dsonar.login='${SONAR_PASSWORD}'"
        }else if (env.CHANGE_ID){
            println 'Performing pull request Analysis';
            sh "./gradlew -b "+buildFileLocation+" sonar -Dsonar.github.pullRequest='${env.CHANGE_ID}' -Dsonar.host.url='${SONAR_URL}' -Dsonar.login='${SONAR_PASSWORD}' -Dsonar.analysis.mode=preview -Dsonar.github.oauth=a9e63f6378746a280bf0756c2844d2266cca7dc9 -Dsonar.github.repository='${env.CUSTOM_SONAR_REPO_NAME}' -i"
        }else{
            println 'Running Sonar analysis on branch';
            sh "./gradlew -b "+buildFileLocation+" sonar -Dsonar.host.url='${SONAR_URL}' -Dsonar.login="+getRepoName()+ -Dsonar.github.repository='${env.CUSTOM_SONAR_REPO_NAME}' -Dsonar.branch='${env.GIT_BRANCH}' -i"
        }
    }  
}

return this;
