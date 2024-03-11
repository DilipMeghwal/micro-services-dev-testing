FROM openjdk:17.0.1-jdk-slim
VOLUME /tmp
EXPOSE 8090
ADD target/ms-0.0.1.jar country-app.jar
ENTRYPOINT ["java", "-jar", "/country-app.jar"]