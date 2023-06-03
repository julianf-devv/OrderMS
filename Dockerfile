FROM eclipse-temurin:17.0.7_7-jre-jammy
ENV APP_HOME=/usr/app/
ENV APP_NAME=ana-mar-0.0.1-SNAPSHOT.jar
WORKDIR $APP_HOME
COPY target/$APP_NAME .
EXPOSE 8080
CMD ["java", "-jar", "ana-mar-0.0.1-SNAPSHOT.jar"]