package com.conectatarot.backend.service;

import com.conectatarot.backend.entity.DisponibilidadTarotista;
import com.conectatarot.backend.entity.Tarotista;
import com.conectatarot.backend.repository.DisponibilidadTarotistaRepository;
import com.conectatarot.backend.repository.TarotistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DisponibilidadTarotistaService {

    private final DisponibilidadTarotistaRepository repository;
    private final TarotistaRepository tarotistaRepository;

    public void crearDisponibilidad(
            Integer tarotistaId,
            String dia,
            String horaInicio,
            String horaFin
    ) {

        Tarotista tarotista = tarotistaRepository.findById(tarotistaId)
                .orElseThrow();

        DisponibilidadTarotista disponibilidad =
                DisponibilidadTarotista.builder()
                        .tarotista(tarotista)
                        .diaSemana(dia)
                        .horaInicio(LocalTime.parse(horaInicio))
                        .horaFin(LocalTime.parse(horaFin))
                        .activa(true)
                        .build();

        repository.save(disponibilidad);
    }
}