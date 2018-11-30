pipeline {

    agent none
    
    parameters {
        string(defaultValue: "0.0", description: 'Build version prefix', name: 'BUILD_VERSION_PREFIX')
        string(defaultValue: "", description: 'Build number offset', name: 'BUILDS_OFFSET')
    }
    
    triggers {
        snapshotDependencies()
        upstream(upstreamProjects: [ 'kurlytail/utility-lib/master', 'kurlytail/user-authentication/master' ], threshold: hudson.model.Result.SUCCESS)
    }

    stages {
        stage('Prepare env') {
            agent {
                label 'master'
            }
            
            steps {
                script {
                    loadLibrary()
                    env['MAVEN_VERSION_NUMBER'] = getMavenVersion 'kurlytail/user-registration/master', params.BUILD_VERSION_PREFIX, params.BUILDS_OFFSET
                    env['NPM_VERSION_NUMBER'] = getNpmVersion 'kurlytail/user-registration/master', params.BUILD_VERSION_PREFIX, params.BUILDS_OFFSET
                    currentBuild.displayName = env['MAVEN_VERSION_NUMBER']
                }
            }
        }
        
        stage ('Maven Build') {
            agent {
                label 'mvn'
            }
            
            steps {
                sh 'rm -rf *'
     
                checkout scm
                withMaven {
	                sh '/usr/local/bin/mvn --batch-mode release:update-versions -DautoVersionSubmodules=true -DdevelopmentVersion=$MAVEN_VERSION_NUMBER'
	                sh '/usr/local/bin/mvn -s settings.xml deploy' 
                }
            }
        }

        stage ('NPM Build') {
            agent {
                label 'node-build'
            }
            
            steps {
                sh 'rm -rf *'
     
                checkout scm
                
                nodejs(nodeJSInstallationName: 'Node') {
	                sh 'npm install'
	                sh 'npm version $NPM_VERSION_NUMBER'
	                sh 'npm run lint'
	                sh 'npm run test'           
	                publishHTML target: [
	                    allowMissing: false,
	                    alwaysLinkToLastBuild: false,
	                    keepAll: true,
	                    reportDir: 'coverage',
	                    reportFiles: 'index.html',
	                    reportName: 'Coverage Report'
	                ]
	                junit 'test-report.xml'
	                sh 'npm run build'
	                sh 'npm publish'
                }
            }
        }
    }
}

