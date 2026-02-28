package cl.duoc.monitororacle.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "UBICACION_EVENTO")
public class UbicacionEvento {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "VEHICULO_ID", nullable = false)
  private String vehiculoId;

  @Column(name = "LAT", nullable = false)
  private double lat;

  @Column(name = "LNG", nullable = false)
  private double lng;

  @Column(name = "TS_EVENTO", nullable = false)
  private Instant tsEvento;

  public UbicacionEvento() {}

  public UbicacionEvento(String vehiculoId, double lat, double lng, Instant tsEvento) {
    this.vehiculoId = vehiculoId;
    this.lat = lat;
    this.lng = lng;
    this.tsEvento = tsEvento;
  }

  public Long getId() { return id; }
  public String getVehiculoId() { return vehiculoId; }
  public double getLat() { return lat; }
  public double getLng() { return lng; }
  public Instant getTsEvento() { return tsEvento; }

  public void setLat(double lat) { this.lat = lat; }
  public void setLng(double lng) { this.lng = lng; }
}
