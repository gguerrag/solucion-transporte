package cl.duoc.monitororacle.model;

import java.time.Instant;

public record HorarioMsg(
    String vehiculoId,
    String paradero,
    int etaMinutos,
    Instant timestamp
) {}
