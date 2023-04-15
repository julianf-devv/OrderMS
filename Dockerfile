# Definimos la imagen base de nuestro contenedor
FROM openjdk:17-jdk-alpine

# Definimos las variables de entorno de nuestra aplicación
ENV APP_HOME=/usr/app/
ENV APP_NAME=ana-mar-0.0.1-SNAPSHOT.jar

# Creamos la carpeta de nuestra aplicación y nos movemos a ella
WORKDIR $APP_HOME

# Copiamos nuestra aplicación a la carpeta de trabajo del contenedor
COPY target/$APP_NAME $APP_HOME

# Exponemos el puerto en el que nuestra aplicación escucha peticiones
EXPOSE 8080

# Definimos el comando para ejecutar nuestra aplicación
CMD ["java", "-jar", "ana-mar-0.0.1-SNAPSHOT.jar"]