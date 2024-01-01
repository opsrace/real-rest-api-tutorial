#docker pull eclipse-temurin:21-jre-alpine
FROM eclipse-temurin:21-jre-alpine
RUN mkdir /opt/app
COPY target/api-example-0.0.1-SNAPSHOT.jar /opt/app/japp.jar

CMD ["java", "-jar", "/opt/app/japp.jar"]