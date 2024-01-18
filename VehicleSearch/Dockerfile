FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} vehiclesearch.jar
ENTRYPOINT ["java","-jar","/vehiclesearch.jar"]
EXPOSE 9191