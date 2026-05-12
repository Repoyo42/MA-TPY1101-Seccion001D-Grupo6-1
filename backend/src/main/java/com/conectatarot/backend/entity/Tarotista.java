package com.conectatarot.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tarotista")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarotista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(name = "nombre_profesional", nullable = false, length = 100)
    private String nombreProfesional;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "precio_base", precision = 10, scale = 2)
    private BigDecimal precioBase;

    @Column(nullable = false, length = 20)
    private String estado = "PENDIENTE";

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "tarotista")
    private List<TarotistaEspecialidad> tarotistaEspecialidades;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();

        if (this.estado == null) {
            this.estado = "PENDIENTE";
        }
    }
}