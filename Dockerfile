FROM openjdk:8-jdk-alpine
COPY target/product-service.jar product-service.jar
ENTRYPOINT ["java", "-jar", "product-service.jar"]