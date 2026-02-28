package cl.duoc.procesadorhorarios.model;

import java.time.Instant;

public record UbicacionMsg(
    String vehiculoId,
    double lat,
    double lng,
    Instant timestamp
) {}