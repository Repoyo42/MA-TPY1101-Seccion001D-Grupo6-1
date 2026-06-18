package com.conectatarot.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class RegistroTarotistaRequest {

    private String nombre;
    private String email;
    private String password;

    private String nombreProfesional;
    private String descripcion;
    private BigDecimal precioBase;

    private List<Integer> especialidades;

    private List<DisponibilidadRequestDTO> disponibilidades;
}