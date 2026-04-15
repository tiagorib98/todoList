# 1. Usar um computador virtual com Maven e Java 17 para construir o projeto
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# 2. Usar um computador virtual mais leve apenas para correr a aplicação final
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]