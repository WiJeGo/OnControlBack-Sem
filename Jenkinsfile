pipeline {
    agent any
    
    tools {
        maven 'Maven'  // Necesitas configurar Maven en Jenkins
        jdk 'JDK21'     // Necesitas configurar JDK 21 en Jenkins
    }
    
    environment {
        JAVA_HOME = "${tool 'JDK21'}"
        M2_HOME = "${tool 'Maven'}"
        PATH = "${env.JAVA_HOME}/bin:${env.M2_HOME}/bin:${env.PATH}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo 'Obteniendo código del repositorio...'
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                echo 'Compilando el proyecto con Maven...'
                sh './mvnw clean compile'
            }
        }
        
        stage('Test') {
            steps {
                echo 'Ejecutando pruebas unitarias...'
                sh './mvnw test'
            }
            post {
                always {
                    // Publicar resultados de las pruebas
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo 'Empaquetando la aplicación...'
                sh './mvnw package -DskipTests'
            }
        }
        
        stage('Archive') {
            steps {
                echo 'Archivando artefactos...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline ejecutado exitosamente!'
        }
        failure {
            echo 'Pipeline falló. Revisar logs para más detalles.'
        }
        always {
            echo 'Limpieza de archivos temporales...'
            cleanWs()
        }
    }
}

