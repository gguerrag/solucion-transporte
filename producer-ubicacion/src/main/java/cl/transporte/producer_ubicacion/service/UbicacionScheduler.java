package cl.transporte.producer_ubicacion.service;

import cl.transporte.producer_ubicacion.dto.UbicacionMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Service
public class UbicacionScheduler {

  private final KafkaTemplate<String, UbicacionMsg> kafkaTemplate;
  private final String topicUbicaciones;
  private final List<String> vehiculos;
  private final Random random = new Random();

  public UbicacionScheduler(
      KafkaTemplate<String, UbicacionMsg> kafkaTemplate,
      @Value("${app.topic.ubicaciones}") String topicUbicaciones,
      @Value("${producer.vehiculos}") String vehiculosCsv
  ) {
    this.kafkaTemplate = kafkaTemplate;
    this.topicUbicaciones = topicUbicaciones;
    this.vehiculos = List.of(vehiculosCsv.split(","));
  }

  @Scheduled(fixedRate = 1000)
  public void publishCadaSegundo() {
    String vehiculoId = vehiculos.get(random.nextInt(vehiculos.size()));

    double lat = -33.45 + (random.nextDouble() - 0.5) * 0.02;
    double lng = -70.66 + (random.nextDouble() - 0.5) * 0.02;

    UbicacionMsg msg = new UbicacionMsg(vehiculoId, lat, lng, Instant.now());
    kafkaTemplate.send(topicUbicaciones, vehiculoId, msg);
  }
}