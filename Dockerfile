FROM amazoncorretto:21-alpine-jdk
EXPOSE 8080

ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} app.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
