package com.conectatarot.backend.controller;

import com.conectatarot.backend.dto.ApiResponse;
import com.conectatarot.backend.dto.TarotistaEspecialidadResponseDTO;
import com.conectatarot.backend.service.TarotistaEspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarotistas")
@RequiredArgsConstructor
public class TarotistaEspecialidadController {

    private final TarotistaEspecialidadService service;

    @PostMapping("/{tarotistaId}/especialidades")
    public ResponseEntity<ApiResponse<TarotistaEspecialidadResponseDTO>> agregarEspecialidad(
            @PathVariable Integer tarotistaId,
            @RequestBody EspecialidadRequest request
    ) {
        TarotistaEspecialidadResponseDTO especialidad =
                service.agregarEspecialidad(tarotistaId, request.getEspecialidadId());

        return ResponseEntity.ok(
                ApiResponse.ok("Especialidad agregada correctamente", especialidad)
        );
    }

    @DeleteMapping("/{tarotistaId}/especialidades/{especialidadId}")
    public ResponseEntity<ApiResponse<String>> eliminarEspecialidad(
            @PathVariable Integer tarotistaId,
            @PathVariable Integer especialidadId
    ) {
        service.eliminarEspecialidad(tarotistaId, especialidadId);

        return ResponseEntity.ok(
                ApiResponse.ok("Especialidad eliminada correctamente", null)
        );
    }

    @GetMapping("/{tarotistaId}/especialidades")
    public ResponseEntity<ApiResponse<List<TarotistaEspecialidadResponseDTO>>> listarEspecialidades(
            @PathVariable Integer tarotistaId
    ) {
        List<TarotistaEspecialidadResponseDTO> especialidades =
                service.listarEspecialidades(tarotistaId);

        return ResponseEntity.ok(
                ApiResponse.ok("Especialidades obtenidas correctamente", especialidades)
        );
    }

    public static class EspecialidadRequest {
        private Integer especialidadId;

        public Integer getEspecialidadId() {
            return especialidadId;
        }

        public void setEspecialidadId(Integer especialidadId) {
            this.especialidadId = especialidadId;
        }
    }
}