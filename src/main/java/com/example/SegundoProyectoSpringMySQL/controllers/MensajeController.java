package com.example.SegundoProyectoSpringMySQL.controllers;

import com.example.SegundoProyectoSpringMySQL.entities.Categoria;
import com.example.SegundoProyectoSpringMySQL.entities.Mensaje;
import com.example.SegundoProyectoSpringMySQL.repositories.CategoriaRepository;
import com.example.SegundoProyectoSpringMySQL.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MensajeController {

    @Autowired
    MensajeRepository mensajeRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    public void MensajeControler(){
        Categoria categoria = Categoria.builder()
                .nombreCategoria("Móviles")
                .mensajes(List.of(
                        Mensaje.builder()
                                .titulo("Primer post sobre móviles")
                                .texto("Este es el texto del primer post")
                                .fechaCreacion(LocalDateTime.now())
                                .build(),
                        Mensaje.builder()
                                .titulo("Segundo post sobre móviles")
                                .texto("Este es el texto del segundo post")
                                .fechaCreacion(LocalDateTime.now())
                                .build()
                ))
                .build();

        categoriaRepository.save(categoria);
    }


    @GetMapping("/mensajes")
    public List<Mensaje> findAllMensajes(){
        return mensajeRepository.findAll();
    }

    @GetMapping("/mensajes/{id}")
    public Mensaje findMensajes(){
        return new Mensaje();
    }

    @DeleteMapping("/mensajes/{id}")
    public Mensaje removeMensajes(){
        return new Mensaje();
    }

    @PutMapping("/mensajes/{id}")
    public Mensaje editMensajes(@RequestBody Mensaje mensaje){
        return new Mensaje();
    }

    @PostMapping("/mensajes")
    public Mensaje insertMensajes(@RequestBody Mensaje mensaje){
        return new Mensaje();
    }
}
