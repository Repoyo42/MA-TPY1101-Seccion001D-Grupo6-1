package com.conectatarot.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TarotistaEspecialidadResponseDTO {

    private Integer id;
    private Integer especialidadId;
    private String nombre;
    private String descripcion;
}