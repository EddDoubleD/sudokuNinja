FROM  bellsoft/liberica-openjdk-alpine:17.0.6
LABEL maintainer="sulimov.dmitriy@otr.ru"
VOLUME /src
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]