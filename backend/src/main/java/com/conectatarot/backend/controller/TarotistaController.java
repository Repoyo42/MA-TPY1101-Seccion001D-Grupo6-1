package com.conectatarot.backend.controller;

import com.conectatarot.backend.dto.ApiResponse;
import com.conectatarot.backend.dto.PerfilTarotistaDTO;
import com.conectatarot.backend.dto.TarotistaResponseDTO;
import com.conectatarot.backend.entity.Tarotista;
import com.conectatarot.backend.service.TarotistaService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarotistas")
@RequiredArgsConstructor
public class TarotistaController {

    private final TarotistaService tarotistaService;

    @PostMapping
    public ResponseEntity<ApiResponse<Tarotista>> crearTarotista(
            @Valid @RequestBody CrearTarotistaRequest request
    ) {
        Tarotista tarotista = tarotistaService.crearTarotista(
                request.getUsuarioId(),
                request.getNombreProfesional()
        );

        return ResponseEntity.status(201)
                .body(ApiResponse.ok("Tarotista creado correctamente", tarotista));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TarotistaResponseDTO>>> buscarTarotistas(
            @RequestParam(required = false) String especialidad
    ) {
        List<TarotistaResponseDTO> tarotistas =
                tarotistaService.buscarTarotistas(especialidad);

        return ResponseEntity.ok(
                ApiResponse.ok("Tarotistas obtenidos correctamente", tarotistas)
        );
    }

    @PutMapping("/{id}/perfil")
    public ResponseEntity<ApiResponse<Tarotista>> actualizarPerfil(
            @PathVariable Integer id,
            @Valid @RequestBody PerfilTarotistaDTO request,
            Authentication authentication
    ) {
        Tarotista tarotista = tarotistaService.actualizarPerfil(
                id,
                authentication.getName(),
                request.getDescripcion(),
                request.getPrecioBase()
        );

        return ResponseEntity.ok(
                ApiResponse.ok("Perfil actualizado correctamente", tarotista)
        );
    }

    @Getter
    @Setter
    public static class CrearTarotistaRequest {
        private Integer usuarioId;
        private String nombreProfesional;
    }
}