package com.example.consumer.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "UBICACIONES_VEHICULO")
public class UbicacionVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UBI_SEQ")
    @SequenceGenerator(name = "UBI_SEQ", sequenceName = "UBICACIONES_VEHICULO_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "VEHICULO_ID")
    private String vehiculoId;

    @Column(name = "LAT")
    private Double lat;

    @Column(name = "LNG")
    private Double lng;

    @Column(name = "TS")
    private Instant timestamp;

    public UbicacionVehiculo() {}

    public UbicacionVehiculo(String vehiculoId, Double lat, Double lng, Instant timestamp) {
        this.vehiculoId = vehiculoId;
        this.lat = lat;
        this.lng = lng;
        this.timestamp = timestamp;
    }

    // getters/setters si los necesitas
}
