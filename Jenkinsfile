pipeline {
    agent any
        stages {
            stage('Build') {
                steps {
                    sh "ls"
                    sh "gradle assembleDebug"
                    sh 'sudo cp -R app/build/outputs/apk/debug/*.apk /mnt/sdb/app'
                }
            }
            stage('Test') {
                steps {
                    echo 'This is the Testing Stage'
                }
            }
            stage('Deploy') {
		when {
		 branch 'production'
		}
                steps {
                  sh 'curl -X POST -H "Content-Type: application/json" -d @app/build/outputs/apk/debug/output.json http://central.oltinolma.uz/open/set_mobile_current_release_info'
                }
            }
        }
    }

