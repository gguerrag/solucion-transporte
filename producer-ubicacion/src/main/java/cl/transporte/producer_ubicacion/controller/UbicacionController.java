package cl.transporte.producer_ubicacion.controller;

import cl.transporte.producer_ubicacion.dto.UbicacionMsg;
import cl.transporte.producer_ubicacion.service.UbicacionProducer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    private final UbicacionProducer producer;

    public UbicacionController(UbicacionProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> publicar(@Valid @RequestBody UbicacionMsg body) {
        producer.publish(body);
        return ResponseEntity.ok("OK - publicado en RabbitMQ (ubicacion.actualizada)");
    }
}
