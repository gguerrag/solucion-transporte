package cl.transporte.producer_horario.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public class HorarioMsg {

    @NotBlank
    @JsonAlias({"idBus"})
    public String vehiculoId;

    @NotBlank
    public String ruta;

    @NotBlank
    public String horaSalida;

    @NotBlank
    public String horaLlegada;

    @NotBlank
    public String tipoEvento;

    @NotBlank
    public String timestamp;
}
