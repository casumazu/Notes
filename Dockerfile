FROM openjdk:17-oracle
COPY target/*.jar notes-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/notes-0.0.1-SNAPSHOT.jar"]