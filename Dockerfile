FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} wholesale.jar
ENTRYPOINT ["java","-jar","/wholesale.jar"]