package com.conectatarot.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AdminPagoDTO {

    private Integer id;
    private Integer idSesion;
    private String nombreCliente;
    private String nombreTarotista;
    private BigDecimal monto;
    private String estadoPago;
    private LocalDateTime fechaPago;
}
