node {
    stage("checkout") {
        git branch: 'development', url: 'https://github.com/Diluv/API'
    }

    stage("build") {
        sh './gradlew clean shadowJar'
    }

    stage("deploy") {
        if (env.BRANCH_NAME == 'master') {

        } else {
            ansiblePlaybook('dev-deploy.yml')
        }
    }
}