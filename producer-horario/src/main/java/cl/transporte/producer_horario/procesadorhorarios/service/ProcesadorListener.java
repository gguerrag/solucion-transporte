package cl.duoc.procesadorhorarios.service;

import cl.duoc.procesadorhorarios.model.HorarioMsg;
import cl.duoc.procesadorhorarios.model.UbicacionMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ProcesadorListener {

  private final KafkaTemplate<String, HorarioMsg> kafkaTemplate;
  private final String topicHorarios;

  private volatile HorarioMsg ultimo;

  public ProcesadorListener(
      KafkaTemplate<String, HorarioMsg> kafkaTemplate,
      @Value("${app.topic.horarios}") String topicHorarios
  ) {
    this.kafkaTemplate = kafkaTemplate;
    this.topicHorarios = topicHorarios;
  }

  @KafkaListener(topics = "${app.topic.ubicaciones}", groupId = "procesador-horarios")
  public void onUbicacion(UbicacionMsg ubicacion) {
    // Lógica simple: paradero por cuadrantes (suficiente para la entrega)
    String paradero = paraderoPorZona(ubicacion.lat(), ubicacion.lng());
    int eta = etaSimple(ubicacion.lat(), ubicacion.lng());

    HorarioMsg msg = new HorarioMsg(
        ubicacion.vehiculoId(),
        paradero,
        eta,
        ubicacion.timestamp() != null ? ubicacion.timestamp() : Instant.now()
    );

    ultimo = msg;
    kafkaTemplate.send(topicHorarios, msg.vehiculoId(), msg);
  }

  private String paraderoPorZona(double lat, double lng) {
    if (lat < -33.455) return "PAR-01";
    if (lat < -33.448) return "PAR-02";
    return "PAR-03";
  }

  private int etaSimple(double lat, double lng) {
    double base = Math.abs(lat + 33.45) * 1000 + Math.abs(lng + 70.66) * 1000;
    int eta = (int)Math.max(1, Math.min(10, Math.round(base)));
    return eta;
  }

  public HorarioMsg getUltimo() {
    return ultimo;
  }
}