package com.example.SegundoProyectoSpringMySQL.controllers;

import com.example.SegundoProyectoSpringMySQL.DTO.MensajeDTO;
import com.example.SegundoProyectoSpringMySQL.DTO.MensajeRequest;
import com.example.SegundoProyectoSpringMySQL.entities.Mensaje;
import com.example.SegundoProyectoSpringMySQL.services.MensajeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

    private final MensajeService mensajeService;

    public MensajeController(MensajeService mensajeService) {
        this.mensajeService = mensajeService;
    }

    // Obtener todos los mensajes
    @GetMapping
    public ResponseEntity<List<Mensaje>> findAllMensajes() {
        List<Mensaje> mensajes = mensajeService.findAllMensajes();
        if (mensajes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mensajes);
    }

    // Obtener mensajes paginados
    @GetMapping("/paginados")
    public ResponseEntity<Page<Mensaje>> findAllMensajesPaginados(
            @PageableDefault(page = 0, size = 5, sort = "fechaCreacion", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<Mensaje> mensajes = mensajeService.findAllMensajesPaginados(pageable);
        if (mensajes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mensajes);
    }

    // Obtener mensajes simples (DTO)
    @GetMapping("/simple")
    public ResponseEntity<List<MensajeDTO>> findAllMensajesSimple() {
        List<MensajeDTO> mensajesDTO = mensajeService.findAllMensajesSimple();
        if (mensajesDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mensajesDTO);
    }

    // Buscar un mensaje por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mensaje> findMensajeById(@PathVariable Long id) {
        Optional<Mensaje> mensaje = mensajeService.findMensajeById(id);
        return mensaje.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un mensaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMensaje(@PathVariable Long id) {
        boolean deleted = mensajeService.deleteMensaje(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Editar un mensaje
    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> editMensaje(@Valid @RequestBody Mensaje mensaje, @PathVariable Long id) {
        Optional<Mensaje> updated = mensajeService.editMensaje(id, mensaje);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un mensaje (con referencia a una categoría)
    @PostMapping
    public ResponseEntity<Void> insertMensaje(@Valid @RequestBody MensajeRequest mensajeRequest) {
        Optional<Mensaje> mensajeGuardado = mensajeService.insertMensaje(mensajeRequest);
        if (mensajeGuardado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        URI uriMensaje = URI.create("/mensajes/" + mensajeGuardado.get().getId());
        return ResponseEntity.created(uriMensaje).build();
    }
}
