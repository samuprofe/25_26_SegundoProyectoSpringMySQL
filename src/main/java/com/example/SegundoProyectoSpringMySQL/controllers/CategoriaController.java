package com.example.SegundoProyectoSpringMySQL.controllers;

import com.example.SegundoProyectoSpringMySQL.entities.Categoria;
import com.example.SegundoProyectoSpringMySQL.repositories.CategoriaRepository;
import com.example.SegundoProyectoSpringMySQL.repositories.MensajeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final MensajeRepository mensajeRepository;

    public CategoriaController(CategoriaRepository categoriaRepository, MensajeRepository mensajeRepository) {
        this.categoriaRepository = categoriaRepository;
        this.mensajeRepository = mensajeRepository;
    }

    // Listar todas las categorías
    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> findAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categorias);
    }

    // Obtener una categoría por ID
    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> findCategoriaById(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una categoría
    @PostMapping("/categorias")
    public ResponseEntity<Void> createCategoria(@RequestBody Categoria categoria) {
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        URI uri = URI.create("/categorias/" + categoriaGuardada.getId());
        return ResponseEntity.created(uri).build();
    }

    // Actualizar una categoría
    @PutMapping("/categorias/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        return categoriaRepository.findById(id)
                .map(existing -> {
                    existing.setNombreCategoria(categoria.getNombreCategoria());
                    return ResponseEntity.ok(categoriaRepository.save(existing));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una categoría
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        if (!categoriaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Añadir un mensaje a la categoría
    @PostMapping("/categorias/{categoriaId}/mensajes/{mensajeId}")
    public ResponseEntity<Void> addMensajeToCategoria(@PathVariable Long categoriaId, @PathVariable Long mensajeId) {


    }

    // Quitar un mensaje de la categoría
    @DeleteMapping("/categorias/{categoriaId}/mensajes/{mensajeId}")
    public ResponseEntity<Void> removeMensajeFromCategoria(@PathVariable Long categoriaId, @PathVariable Long mensajeId) {

    }
}

//Crear CategoriaController
//Incluir añadir un mensaje a una categoria y borrar un mensaje de una categoria
//Crear