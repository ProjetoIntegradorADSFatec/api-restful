pipeline {
  agent any
  stages {
    stage('Clone') {
      steps {
        git(url: 'https://github.com/ProjetoIntegradorADSFatec/api-restful.git', branch: 'master')
      }
    }

    stage('Environment') {
      steps {
        sh 'pip3 install --upgrade --no-deps --force-reinstall -r requirements.txt'
      }
    }

    stage('Test') {
      parallel {
        stage('PyTest') {
          steps {
            sh 'pytest-3 -v'
          }
        }

      }
    }

    stage('Log') {
      steps {
        readFile 'pytest-log.log'
      }
    }

  }
}