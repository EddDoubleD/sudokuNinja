FROM edddoubled/liberica-openjdk-maven-alpine:17
LABEL maintainer="sulimov.dmitriy@otr.ru"
# copy the source tree and the pom.xml to our new container
COPY ./ ./
# package our application code
RUN mvn clean package
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]