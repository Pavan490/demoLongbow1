FROM eclipse-temurin:21-jdk
COPY target/demolongbow.jar demolongbow.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "demolongbow.jar"]
