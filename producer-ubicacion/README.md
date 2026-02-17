# producer-ubicacion

Microservicio **productor** (Spring Boot) para publicar mensajes en RabbitMQ.

## Ejecutar local
```bash
./mvnw clean package
./mvnw spring-boot:run
```

## Endpoint
- POST http://localhost:8081/api/ubicaciones

## Health
- GET /actuator/health
