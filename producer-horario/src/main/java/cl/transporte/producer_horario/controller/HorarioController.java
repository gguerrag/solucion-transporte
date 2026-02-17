package cl.transporte.producer_horario.controller;

import cl.transporte.producer_horario.dto.HorarioMsg;
import cl.transporte.producer_horario.service.HorarioProducer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    private final HorarioProducer producer;

    public HorarioController(HorarioProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> publicar(@Valid @RequestBody HorarioMsg body) {
        producer.publish(body);
        return ResponseEntity.ok("OK - publicado en RabbitMQ (horario.actualizado)");
    }
}
