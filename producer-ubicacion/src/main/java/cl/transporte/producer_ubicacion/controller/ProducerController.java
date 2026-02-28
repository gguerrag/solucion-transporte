package cl.transporte.producer_ubicacion.controller;

import cl.transporte.producer_ubicacion.dto.UbicacionMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api")
public class ProducerController {

  private final KafkaTemplate<String, UbicacionMsg> kafkaTemplate;
  private final String topic;

  public ProducerController(
      KafkaTemplate<String, UbicacionMsg> kafkaTemplate,
      @Value("${app.topic.ubicaciones}") String topic
  ) {
    this.kafkaTemplate = kafkaTemplate;
    this.topic = topic;
  }

  @PostMapping("/ubicaciones/publish")
  public String publish(@RequestBody UbicacionMsg body) {
    UbicacionMsg msg = new UbicacionMsg(
        body.vehiculoId(),
        body.lat(),
        body.lng(),
        body.timestamp() != null ? body.timestamp() : Instant.now()
    );
    kafkaTemplate.send(topic, msg.vehiculoId(), msg);
    return "OK";
  }

  @GetMapping("/health")
  public String health() {
    return "UP";
  }

  @PutMapping("/config")
  public String config() {
    return "OK";
  }

  @DeleteMapping("/config")
  public String reset() {
    return "OK";
  }
}