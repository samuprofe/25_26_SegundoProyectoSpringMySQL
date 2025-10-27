package com.example.SegundoProyectoSpringMySQL.controllers;

import com.example.SegundoProyectoSpringMySQL.entities.Categoria;
import com.example.SegundoProyectoSpringMySQL.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<Categoria>> findAllCategorias() {
        List<Categoria> categorias = categoriaService.findAllCategorias();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findCategoriaById(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaService.findCategoriaById(id);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una categoría
    @PostMapping
    public ResponseEntity<Void> createCategoria(@RequestBody Categoria categoria) {
        Categoria categoriaGuardada = categoriaService.createCategoria(categoria);
        URI uri = URI.create("/categorias/" + categoriaGuardada.getId());
        return ResponseEntity.created(uri).build();
    }

    // Actualizar una categoría
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Optional<Categoria> updated = categoriaService.updateCategoria(id, categoria);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        boolean deleted = categoriaService.deleteCategoria(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Añadir un mensaje a una categoría
    @PostMapping("/{categoriaId}/mensajes/{mensajeId}")
    public ResponseEntity<Void> addMensajeToCategoria(@PathVariable Long categoriaId, @PathVariable Long mensajeId) {
        boolean added = categoriaService.addMensajeToCategoria(categoriaId, mensajeId);
        return added ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Quitar un mensaje de la categoría
    @DeleteMapping("/{categoriaId}/mensajes/{mensajeId}")
    public ResponseEntity<Void> removeMensajeFromCategoria(@PathVariable Long categoriaId, @PathVariable Long mensajeId) {
        boolean removed = categoriaService.removeMensajeFromCategoria(categoriaId, mensajeId);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
