FROM openjdk:17-jdk-alpine
ENV APP_HOME=/usr/app/
ENV APP_NAME=ana-mar-0.0.1-SNAPSHOT.jar
WORKDIR $APP_HOME
COPY target/$APP_NAME $APP_HOME
EXPOSE 8080
CMD ["java", "-jar", "ana-mar-0.0.1-SNAPSHOT.jar"]