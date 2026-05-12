package com.conectatarot.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "disponibilidad_tarotista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisponibilidadTarotista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tarotista_id", nullable = false)
    private Tarotista tarotista;

    @Column(name = "dia_semana", nullable = false, length = 20)
    private String diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false)
    private Boolean activa = true;
}