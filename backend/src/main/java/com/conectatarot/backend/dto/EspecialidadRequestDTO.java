package com.conectatarot.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EspecialidadRequestDTO {

    @NotNull
    private Integer especialidadId;
}