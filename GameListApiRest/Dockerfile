# ===== BUILD STAGE =====
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# copiar pom primero
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copiar código
COPY src ./src

# compilar jar
RUN mvn clean package -DskipTests

# ===== RUNTIME STAGE =====
FROM eclipse-temurin:17-jdk

WORKDIR /app

# copiar jar generado
COPY --from=build /app/target/*.jar app.jar

# puerto de Spring Boot
EXPOSE 8080

# ejecutar app
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.datasource.url=${SPRING_DATASOURCE_URL}", "--spring.datasource.username=${SPRING_DATASOURCE_USERNAME}", "--spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}"]