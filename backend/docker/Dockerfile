FROM amd64/eclipse-temurin:11-jre-alpine
WORKDIR /app
COPY target/*.jar restaurant-backend.jar
EXPOSE 8080
CMD ["java", "-jar", "restaurant-backend.jar"]