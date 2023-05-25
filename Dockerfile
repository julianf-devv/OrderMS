FROM openjdk:17-jdk-alpine3.14
ENV APP_HOME=/usr/app/
ENV APP_NAME=ana-mar-0.0.1-SNAPSHOT.jar
WORKDIR $APP_HOME
COPY target/$APP_NAME .
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
RUN chown appuser:appgroup $APP_NAME
USER appuser
EXPOSE 8080
CMD ["java", "-jar", "$APP_NAME"]