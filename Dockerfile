FROM bellsoft/liberica-openjdk-alpine:17.0.6
LABEL maintainer="sulimov.dmitriy@otr.ru"
# copy the source tree and the pom.xml to our new container
COPY ./ ./
# package our application code
RUN ./mvnw package -DskipTests
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]