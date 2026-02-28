package cl.duoc.monitororacle.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "HORARIO_EVENTO")
public class HorarioEvento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "VEHICULO_ID", nullable = false)
  private String vehiculoId;

  @Column(name = "PARADERO", nullable = false)
  private String paradero;

  @Column(name = "ETA_MIN", nullable = false)
  private int etaMin;

  @Column(name = "TS_EVENTO", nullable = false)
  private Instant tsEvento;

  public HorarioEvento() {}

  public HorarioEvento(String vehiculoId, String paradero, int etaMin, Instant tsEvento) {
    this.vehiculoId = vehiculoId;
    this.paradero = paradero;
    this.etaMin = etaMin;
    this.tsEvento = tsEvento;
  }

  public Long getId() { return id; }
  public String getVehiculoId() { return vehiculoId; }
  public String getParadero() { return paradero; }
  public int getEtaMin() { return etaMin; }
  public Instant getTsEvento() { return tsEvento; }

  public void setParadero(String paradero) { this.paradero = paradero; }
  public void setEtaMin(int etaMin) { this.etaMin = etaMin; }
}
