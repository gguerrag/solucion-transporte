package cl.duoc.monitororacle.repo;

import cl.duoc.monitororacle.entity.UbicacionEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface UbicacionRepo extends JpaRepository<UbicacionEvento, Long> {
  List<UbicacionEvento> findByVehiculoIdAndTsEventoBetween(String vehiculoId, Instant start, Instant end);
}
