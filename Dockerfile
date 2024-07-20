FROM amazoncorretto:17-alpine-jdk
COPY target/product-inventory-system-1.0.0.jar product-inventory-system-1.0.0.jar
ENTRYPOINT ["java","-jar","/product-inventory-system-1.0.0.jar"]