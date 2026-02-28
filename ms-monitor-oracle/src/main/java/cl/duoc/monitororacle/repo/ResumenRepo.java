package cl.duoc.monitororacle.repo;

import cl.duoc.monitororacle.entity.ResumenDia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ResumenRepo extends JpaRepository<ResumenDia, Long> {
  List<ResumenDia> findByFecha(LocalDate fecha);
}
