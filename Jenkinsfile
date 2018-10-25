pipeline {
  agent any
  stages{
    stage('Build'){
      steps{
        sh '/usr/share/maven/bin/mvn -f src/pom.xml clean install'
      }
    }
  }
}
