package cl.duoc.monitororacle.model;

import java.time.Instant;

public record UbicacionMsg(
    String vehiculoId,
    double lat,
    double lng,
    Instant timestamp
) {}
