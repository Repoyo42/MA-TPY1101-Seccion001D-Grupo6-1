package com.conectatarot.backend.controller;

import com.conectatarot.backend.dto.RegistroUsuarioRequest;
import com.conectatarot.backend.dto.UsuarioResponse;
import com.conectatarot.backend.dto.EliminarCuentaRequest;
import com.conectatarot.backend.dto.ApiResponse;
import com.conectatarot.backend.entity.Usuario;
import com.conectatarot.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroUsuarioRequest request) {
        try {

            // Crear entidad Usuario desde el DTO
            Usuario usuario = new Usuario();
            usuario.setNombre(request.getNombre());
            usuario.setEmail(request.getEmail());
            usuario.setPassword(request.getPassword());

            // Guardar usuario
            Usuario usuarioGuardado = usuarioService.registrarUsuario(usuario);

            // Crear response limpio (SIN PASSWORD)
            UsuarioResponse response = new UsuarioResponse();
            response.setIdUsuario(usuarioGuardado.getIdUsuario());
            response.setNombre(usuarioGuardado.getNombre());
            response.setEmail(usuarioGuardado.getEmail());
            response.setRol(usuarioGuardado.getRol().getNombreRol());
            response.setActivo(usuarioGuardado.getActivo());
            response.setFechaRegistro(
                    usuarioGuardado.getFechaRegistro() != null
                            ? usuarioGuardado.getFechaRegistro().toString()
                            : null
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
     public ResponseEntity<?> actualizarUsuario(@PathVariable Integer id, @RequestBody RegistroUsuarioRequest request) {
       try {
           Usuario usuario = usuarioService.actualizarUsuario(id, request.getNombre(), request.getEmail());
           UsuarioResponse response = new UsuarioResponse();
           response.setIdUsuario(usuario.getIdUsuario());
           response.setNombre(usuario.getNombre());
           response.setEmail(usuario.getEmail());
           response.setRol(usuario.getRol().getNombreRol());
           response.setActivo(usuario.getActivo());
           return ResponseEntity.ok(response);
       } catch (RuntimeException e) {
       	   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
       }
    }

    @PostMapping("/{id}/eliminar")
    public ResponseEntity<?> eliminarCuenta(@PathVariable Integer id, @RequestBody EliminarCuentaRequest request) {
        try {
            usuarioService.eliminarUsuario(id, request.getPassword());
            return ResponseEntity.ok(ApiResponse.ok("Cuenta eliminada correctamente", null));
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Contraseña incorrecta")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(e.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getMessage()));
        }
    }
}
