package cl.duoc.monitororacle.repo;

import cl.duoc.monitororacle.entity.HorarioEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface HorarioRepo extends JpaRepository<HorarioEvento, Long> {
  List<HorarioEvento> findByVehiculoIdAndTsEventoBetween(String vehiculoId, Instant start, Instant end);
}
