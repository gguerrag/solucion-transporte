package cl.duoc.monitororacle.service;

import cl.duoc.monitororacle.entity.HorarioEvento;
import cl.duoc.monitororacle.entity.UbicacionEvento;
import cl.duoc.monitororacle.model.HorarioMsg;
import cl.duoc.monitororacle.model.UbicacionMsg;
import cl.duoc.monitororacle.repo.HorarioRepo;
import cl.duoc.monitororacle.repo.UbicacionRepo;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MonitorKafkaListener {

  private final UbicacionRepo ubicacionRepo;
  private final HorarioRepo horarioRepo;

  public MonitorKafkaListener(UbicacionRepo ubicacionRepo, HorarioRepo horarioRepo) {
    this.ubicacionRepo = ubicacionRepo;
    this.horarioRepo = horarioRepo;
  }

  @KafkaListener(topics = "${app.topic.ubicaciones}", groupId = "monitor-oracle")
  public void onUbicacion(UbicacionMsg msg) {
    Instant ts = msg.timestamp() != null ? msg.timestamp() : Instant.now();
    ubicacionRepo.save(new UbicacionEvento(msg.vehiculoId(), msg.lat(), msg.lng(), ts));
  }

  @KafkaListener(topics = "${app.topic.horarios}", groupId = "monitor-oracle")
  public void onHorario(HorarioMsg msg) {
    Instant ts = msg.timestamp() != null ? msg.timestamp() : Instant.now();
    horarioRepo.save(new HorarioEvento(msg.vehiculoId(), msg.paradero(), msg.etaMinutos(), ts));
  }
}
