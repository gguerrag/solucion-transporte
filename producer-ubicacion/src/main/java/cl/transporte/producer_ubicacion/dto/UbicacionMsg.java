package cl.transporte.producer_ubicacion.dto;

import java.time.Instant;

public record UbicacionMsg(
    String vehiculoId,
    double lat,
    double lng,
    Instant timestamp
) {}