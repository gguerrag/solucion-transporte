package cl.duoc.monitororacle.service;

import cl.duoc.monitororacle.entity.HorarioEvento;
import cl.duoc.monitororacle.entity.ResumenDia;
import cl.duoc.monitororacle.entity.UbicacionEvento;
import cl.duoc.monitororacle.repo.HorarioRepo;
import cl.duoc.monitororacle.repo.ResumenRepo;
import cl.duoc.monitororacle.repo.UbicacionRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ResumenService {

  private final UbicacionRepo ubicacionRepo;
  private final HorarioRepo horarioRepo;
  private final ResumenRepo resumenRepo;

  private final ZoneId zone;

  public ResumenService(
      UbicacionRepo ubicacionRepo,
      HorarioRepo horarioRepo,
      ResumenRepo resumenRepo,
      @Value("${appzone.tz:America/Santiago}") String tz
  ) {
    this.ubicacionRepo = ubicacionRepo;
    this.horarioRepo = horarioRepo;
    this.resumenRepo = resumenRepo;
    this.zone = ZoneId.of(tz);
  }

  // “Fin del día” (23:59)
  @Scheduled(cron = "0 59 23 * * *")
  public void resumenFinDia() {
    generarResumen(LocalDate.now(zone));
  }

  public List<ResumenDia> generarResumen(LocalDate fecha) {
    Instant start = fecha.atStartOfDay(zone).toInstant();
    Instant end = fecha.plusDays(1).atStartOfDay(zone).toInstant();

    // Nota: como no tenemos query "vehiculos distintos", tomamos BUS-101..BUS-103 para la demo,
    // o bien puedes agregar query distinct en repos.
    List<String> vehiculosDemo = List.of("BUS-101","BUS-102","BUS-103");
    List<ResumenDia> out = new ArrayList<>();

    for (String vehiculo : vehiculosDemo) {
      List<UbicacionEvento> ubis = ubicacionRepo.findByVehiculoIdAndTsEventoBetween(vehiculo, start, end);
      List<HorarioEvento> hors = horarioRepo.findByVehiculoIdAndTsEventoBetween(vehiculo, start, end);
      if (ubis.isEmpty() && hors.isEmpty()) continue;

      Instant primer = Stream.of(
          ubis.stream().map(UbicacionEvento::getTsEvento).min(Comparator.naturalOrder()).orElse(null),
          hors.stream().map(HorarioEvento::getTsEvento).min(Comparator.naturalOrder()).orElse(null)
      ).filter(Objects::nonNull).min(Comparator.naturalOrder()).orElse(null);

      Instant ultimo = Stream.of(
          ubis.stream().map(UbicacionEvento::getTsEvento).max(Comparator.naturalOrder()).orElse(null),
          hors.stream().map(HorarioEvento::getTsEvento).max(Comparator.naturalOrder()).orElse(null)
      ).filter(Objects::nonNull).max(Comparator.naturalOrder()).orElse(null);

      String paraderos = hors.stream()
          .map(HorarioEvento::getParadero)
          .filter(Objects::nonNull)
          .distinct()
          .collect(Collectors.joining(","));

      ResumenDia resumen = new ResumenDia(
          fecha,
          vehiculo,
          ubis.size(),
          hors.size(),
          primer,
          ultimo,
          paraderos
      );
      out.add(resumenRepo.save(resumen));
    }
    return out;
  }
}
