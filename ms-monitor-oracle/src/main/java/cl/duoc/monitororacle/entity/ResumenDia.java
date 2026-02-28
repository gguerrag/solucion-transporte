package cl.duoc.monitororacle.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "RESUMEN_DIA")
public class ResumenDia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "FECHA", nullable = false)
  private LocalDate fecha;

  @Column(name = "VEHICULO_ID", nullable = false)
  private String vehiculoId;

  @Column(name = "TOTAL_UBICACIONES", nullable = false)
  private long totalUbicaciones;

  @Column(name = "TOTAL_HORARIOS", nullable = false)
  private long totalHorarios;

  @Column(name = "PRIMER_EVENTO")
  private Instant primerEvento;

  @Column(name = "ULTIMO_EVENTO")
  private Instant ultimoEvento;

  @Lob
  @Column(name = "PARADEROS_VISITADOS")
  private String paraderosVisitados;

  public ResumenDia() {}

  public ResumenDia(LocalDate fecha, String vehiculoId, long totalUbicaciones, long totalHorarios,
                   Instant primerEvento, Instant ultimoEvento, String paraderosVisitados) {
    this.fecha = fecha;
    this.vehiculoId = vehiculoId;
    this.totalUbicaciones = totalUbicaciones;
    this.totalHorarios = totalHorarios;
    this.primerEvento = primerEvento;
    this.ultimoEvento = ultimoEvento;
    this.paraderosVisitados = paraderosVisitados;
  }

  public Long getId() { return id; }
  public LocalDate getFecha() { return fecha; }
  public String getVehiculoId() { return vehiculoId; }
  public long getTotalUbicaciones() { return totalUbicaciones; }
  public long getTotalHorarios() { return totalHorarios; }
  public Instant getPrimerEvento() { return primerEvento; }
  public Instant getUltimoEvento() { return ultimoEvento; }
  public String getParaderosVisitados() { return paraderosVisitados; }
}
