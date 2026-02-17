package com.example.consumer.listener;

import com.example.consumer.dto.UbicacionMsg;
import com.example.consumer.entity.UbicacionVehiculo;
import com.example.consumer.repo.UbicacionVehiculoRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UbicacionesListener {

    private final UbicacionVehiculoRepo repo;
    private final ObjectMapper mapper = new ObjectMapper();

    public UbicacionesListener(UbicacionVehiculoRepo repo) {
        this.repo = repo;
    }

    @RabbitListener(queues = "${app.queues.ubicaciones}")
    public void recibir(String payload) throws Exception {
        UbicacionMsg msg = mapper.readValue(payload, UbicacionMsg.class);

        Instant ts;
        try {
            if (msg.timestamp == null || msg.timestamp.isBlank()) {
                ts = Instant.now();
            } else {
                // Acepta ISO-8601 con o sin zona. Si no trae zona, asume UTC.
                String t = msg.timestamp.trim();
                if (t.endsWith("Z") || t.matches(".*[+-]\\d\\d:\\d\\d$")) {
                    ts = java.time.OffsetDateTime.parse(t).toInstant();
                } else {
                    ts = java.time.LocalDateTime.parse(t).toInstant(java.time.ZoneOffset.UTC);
                }
            }
        } catch (Exception ex) {
            ts = Instant.now();
        }

        repo.save(new UbicacionVehiculo(msg.vehiculoId, msg.lat, msg.lng, ts));

        System.out.println("[UBI] Guardado en Oracle: " + msg.vehiculoId + " " + msg.lat + "," + msg.lng);
    }
}
