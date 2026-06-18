package com.conectatarot.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisponibilidadRequestDTO {

    private String diaSemana;
    private String horaInicio;
    private String horaFin;
}