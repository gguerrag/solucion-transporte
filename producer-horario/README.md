# producer-horario

Microservicio **productor** (Spring Boot) para publicar mensajes en RabbitMQ.

## Ejecutar local
```bash
./mvnw clean package
./mvnw spring-boot:run
```

## Endpoint
- POST http://localhost:8082/api/horarios

## Health
- GET /actuator/health
