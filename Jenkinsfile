node {
    stage("checkout") {
        git branch: 'development', url: 'https://github.com/Diluv/API'
    }

    stage("build") {
        if (isUnix()) {
            sh './gradlew clean shadowJar'
        } else {
            bat './gradlew.bat clean shadowJar'
        }
    }

    stage("deploy") {
        if (env.BRANCH_NAME == 'master') {
            
        } else {
            if (isUnix()) {
                ansiblePlaybook('deploy.yml')
            } else {
            }
        }
    }
}