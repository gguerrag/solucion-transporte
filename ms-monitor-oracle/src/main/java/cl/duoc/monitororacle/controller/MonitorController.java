package cl.duoc.monitororacle.controller;

import cl.duoc.monitororacle.entity.HorarioEvento;
import cl.duoc.monitororacle.entity.ResumenDia;
import cl.duoc.monitororacle.entity.UbicacionEvento;
import cl.duoc.monitororacle.repo.HorarioRepo;
import cl.duoc.monitororacle.repo.ResumenRepo;
import cl.duoc.monitororacle.repo.UbicacionRepo;
import cl.duoc.monitororacle.service.ResumenService;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MonitorController {

  private final UbicacionRepo ubicacionRepo;
  private final HorarioRepo horarioRepo;
  private final ResumenRepo resumenRepo;
  private final ResumenService resumenService;

  public MonitorController(UbicacionRepo ubicacionRepo, HorarioRepo horarioRepo, ResumenRepo resumenRepo, ResumenService resumenService) {
    this.ubicacionRepo = ubicacionRepo;
    this.horarioRepo = horarioRepo;
    this.resumenRepo = resumenRepo;
    this.resumenService = resumenService;
  }

  @GetMapping("/health")
  public String health() { return "UP"; }

  // GET (consulta por vehículo + fecha)
  @GetMapping("/ubicaciones")
  public List<UbicacionEvento> ubicaciones(@RequestParam String vehiculoId, @RequestParam String fecha) {
    LocalDate d = LocalDate.parse(fecha);
    ZoneId zone = ZoneId.of("America/Santiago");
    Instant start = d.atStartOfDay(zone).toInstant();
    Instant end = d.plusDays(1).atStartOfDay(zone).toInstant();
    return ubicacionRepo.findByVehiculoIdAndTsEventoBetween(vehiculoId, start, end);
  }

  @GetMapping("/horarios")
  public List<HorarioEvento> horarios(@RequestParam String vehiculoId, @RequestParam String fecha) {
    LocalDate d = LocalDate.parse(fecha);
    ZoneId zone = ZoneId.of("America/Santiago");
    Instant start = d.atStartOfDay(zone).toInstant();
    Instant end = d.plusDays(1).atStartOfDay(zone).toInstant();
    return horarioRepo.findByVehiculoIdAndTsEventoBetween(vehiculoId, start, end);
  }

  // DELETE (cumplir CRUD)
  @DeleteMapping("/ubicaciones/{id}")
  public void deleteUbicacion(@PathVariable Long id) {
    ubicacionRepo.deleteById(id);
  }

  @DeleteMapping("/horarios/{id}")
  public void deleteHorario(@PathVariable Long id) {
    horarioRepo.deleteById(id);
  }

  // PUT (cumplir CRUD)
  @PutMapping("/horarios/{id}")
  public HorarioEvento updateHorario(@PathVariable Long id, @RequestBody HorarioEvento body) {
    HorarioEvento h = horarioRepo.findById(id).orElseThrow();
    h.setParadero(body.getParadero());
    h.setEtaMin(body.getEtaMin());
    return horarioRepo.save(h);
  }

  // POST para demo (no esperar 23:59)
  @PostMapping("/resumen/generar")
  public List<ResumenDia> generar(@RequestParam String fecha) {
    return resumenService.generarResumen(LocalDate.parse(fecha));
  }

  @GetMapping("/resumen")
  public List<ResumenDia> resumen(@RequestParam String fecha) {
    return resumenRepo.findByFecha(LocalDate.parse(fecha));
  }
}
