FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/*.jar mejor-project-techno-0.0.1-SNAPSHOT.jar

EXPOSE 9089

ENTRYPOINT ["java","-jar","mejor-project-techno-0.0.1-SNAPSHOT.jar"]