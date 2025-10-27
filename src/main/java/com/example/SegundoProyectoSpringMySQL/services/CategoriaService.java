package com.example.SegundoProyectoSpringMySQL.services;

import com.example.SegundoProyectoSpringMySQL.entities.Categoria;
import com.example.SegundoProyectoSpringMySQL.entities.Mensaje;
import com.example.SegundoProyectoSpringMySQL.repositories.CategoriaRepository;
import com.example.SegundoProyectoSpringMySQL.repositories.MensajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final MensajeRepository mensajeRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, MensajeRepository mensajeRepository) {
        this.categoriaRepository = categoriaRepository;
        this.mensajeRepository = mensajeRepository;
    }

    public List<Categoria> findAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> findCategoriaById(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria createCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> updateCategoria(Long id, Categoria categoria) {
        return categoriaRepository.findById(id).map(existing -> {
            existing.setNombreCategoria(categoria.getNombreCategoria());
            return categoriaRepository.save(existing);
        });
    }

    public boolean deleteCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            return false;
        }
        categoriaRepository.deleteById(id);
        return true;
    }

    public boolean addMensajeToCategoria(Long categoriaId, Long mensajeId) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoriaId);
        Optional<Mensaje> mensajeOpt = mensajeRepository.findById(mensajeId);

        if (categoriaOpt.isEmpty() || mensajeOpt.isEmpty()) {
            return false;
        }

        Categoria categoria = categoriaOpt.get();
        Mensaje mensaje = mensajeOpt.get();

        categoria.getMensajes().add(mensaje);
        categoriaRepository.save(categoria);
        return true;
    }

    public boolean removeMensajeFromCategoria(Long categoriaId, Long mensajeId) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoriaId);
        Optional<Mensaje> mensajeOpt = mensajeRepository.findById(mensajeId);

        if (categoriaOpt.isEmpty() || mensajeOpt.isEmpty()) {
            return false;
        }

        Categoria categoria = categoriaOpt.get();
        Mensaje mensaje = mensajeOpt.get();

        categoria.getMensajes().remove(mensaje);
        categoriaRepository.save(categoria);
        return true;
    }
}
