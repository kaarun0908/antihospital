
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY target/hospital-management-system-1.0.0.jar app.jar
CMD ["java", "-jar", "app.jar"]
