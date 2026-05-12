package com.conectatarot.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SesionRequestDTO {

    private Integer usuarioId;
    private Integer tarotistaId;
    private Integer especialidadId;
    private LocalDateTime fecha;
    private Integer duracionMinutos;
}