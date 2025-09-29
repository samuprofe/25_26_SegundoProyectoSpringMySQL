package com.example.SegundoProyectoSpringMySQL.controllers;

import com.example.SegundoProyectoSpringMySQL.entities.Mensaje;
import com.example.SegundoProyectoSpringMySQL.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MensajeController {

    @Autowired
    MensajeRepository mensajeRepository;

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
