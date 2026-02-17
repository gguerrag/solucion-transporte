package cl.transporte.producer_ubicacion.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UbicacionMsg {

    @NotBlank
    @JsonAlias({"idBus"})
    public String vehiculoId;

    @NotNull
    @JsonAlias({"latitud"})
    public Double lat;

    @NotNull
    @JsonAlias({"longitud"})
    public Double lng;

    @NotBlank
    public String timestamp;
}
