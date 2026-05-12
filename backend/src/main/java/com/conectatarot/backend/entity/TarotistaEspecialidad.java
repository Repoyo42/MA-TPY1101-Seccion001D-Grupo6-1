package com.conectatarot.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tarotista_especialidad",
       uniqueConstraints = @UniqueConstraint(columnNames = {"tarotista_id", "especialidad_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarotistaEspecialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tarotista_id", nullable = false)
    private Tarotista tarotista;

    @ManyToOne
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;
}
