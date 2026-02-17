package com.example.consumer.service;

import com.example.consumer.dto.HorarioMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class HorarioFileWriterService {

    private final ObjectMapper mapper;

    @Value("${app.files.output-dir:./out/horarios}")
    private String outputDir;

    public HorarioFileWriterService() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    public Path write(HorarioMsg msg) throws IOException {
        Files.createDirectories(Path.of(outputDir));

        Instant now = Instant.now();
        if (msg.timestamp != null && !msg.timestamp.isBlank()) {
            try {
                String t = msg.timestamp.trim();
                if (t.endsWith("Z") || t.matches(".*[+-]\\d\\d:\\d\\d$")) {
                    now = OffsetDateTime.parse(t).toInstant();
                } else {
                    now = java.time.LocalDateTime.parse(t).toInstant(ZoneOffset.UTC);
                }
            } catch (Exception ignored) {}
        }

        String ts = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
                .withZone(ZoneOffset.UTC)
                .format(now);

        String safeVeh = (msg.vehiculoId == null ? "UNKNOWN" : msg.vehiculoId).replaceAll("[^a-zA-Z0-9_-]", "_");
        String filename = "horario_" + safeVeh + "_" + ts + ".json";

        Path file = Path.of(outputDir, filename);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), msg);
        return file;
    }
}
