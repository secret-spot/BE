FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY build/libs/app.jar app.jar

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
