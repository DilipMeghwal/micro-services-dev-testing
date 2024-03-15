#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17.0.1-jdk-slim
VOLUME /tmp
EXPOSE 8090
ADD --from=build /home/apptarget/ms-0.0.1.jar /usr/local/lib/country-app.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/country-app.jar"]