FROM openjdk:17
MAINTAINER rohan
COPY target/urlShorteningService-1.0.0-SNAPSHOT.jar urlShorteningService-1.0.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/urlShorteningService-1.0.0-SNAPSHOT.jar"]