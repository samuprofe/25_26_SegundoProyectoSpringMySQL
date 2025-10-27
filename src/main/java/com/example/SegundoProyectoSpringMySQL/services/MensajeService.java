package com.example.SegundoProyectoSpringMySQL.services;

import com.example.SegundoProyectoSpringMySQL.DTO.MensajeDTO;
import com.example.SegundoProyectoSpringMySQL.DTO.MensajeRequest;
import com.example.SegundoProyectoSpringMySQL.entities.Categoria;
import com.example.SegundoProyectoSpringMySQL.entities.Mensaje;
import com.example.SegundoProyectoSpringMySQL.repositories.CategoriaRepository;
import com.example.SegundoProyectoSpringMySQL.repositories.MensajeRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MensajeService {

    private final MensajeRepository mensajeRepository;
    private final CategoriaRepository categoriaRepository;

    public MensajeService(MensajeRepository mensajeRepository, CategoriaRepository categoriaRepository) {
        this.mensajeRepository = mensajeRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Mensaje> findAllMensajes() {
        return mensajeRepository.findAll();
    }

    public Page<Mensaje> findAllMensajesPaginados(Pageable pageable) {
        return mensajeRepository.findAll(pageable);
    }

    public List<MensajeDTO> findAllMensajesSimple() {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        List<MensajeDTO> mensajesDTO = new ArrayList<>();

        mensajes.forEach(mensaje -> {
            String nombreCategoria = "Sin categoria";
            if (mensaje.getCategoria() != null) {
                nombreCategoria = mensaje.getCategoria().getNombreCategoria();
            }
            mensajesDTO.add(
                    MensajeDTO.builder()
                            .id(mensaje.getId())
                            .titulo(mensaje.getTitulo())
                            .texto(mensaje.getTexto())
                            .nombreCategoria(nombreCategoria)
                            .build()
            );
        });

        return mensajesDTO;
    }

    public Optional<Mensaje> findMensajeById(Long id) {
        return mensajeRepository.findById(id);
    }

    public boolean deleteMensaje(Long id) {
        if (!mensajeRepository.existsById(id)) {
            return false;
        }
        mensajeRepository.deleteById(id);
        return true;
    }

    public Optional<Mensaje> editMensaje(Long id, @Valid Mensaje mensaje) {
        return mensajeRepository.findById(id)
                .map(mensajeOriginal -> {
                    mensajeOriginal.setTitulo(mensaje.getTitulo());
                    mensajeOriginal.setTexto(mensaje.getTexto());
                    mensajeOriginal.setCategoria(mensaje.getCategoria());
                    return mensajeRepository.save(mensajeOriginal);
                });
    }

    public Optional<Mensaje> insertMensaje(@Valid MensajeRequest mensajeRequest) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(mensajeRequest.categoria_id());
        if (categoriaOpt.isEmpty()) {
            return Optional.empty();
        }

        Mensaje mensaje = Mensaje.builder()
                .titulo(mensajeRequest.titulo())
                .texto(mensajeRequest.texto())
                .categoria(categoriaOpt.get())
                .build();

        Mensaje mensajeGuardado = mensajeRepository.save(mensaje);
        return Optional.of(mensajeGuardado);
    }
}
