FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} service3.jar
ENTRYPOINT ["java","-jar","/service3.jar"]