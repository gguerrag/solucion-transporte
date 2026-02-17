FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/*.jar app.jar
VOLUME ["/data"]
ENV APP_FILES_OUTPUT_DIR=/data/horarios
ENTRYPOINT ["java","-jar","app.jar"]
