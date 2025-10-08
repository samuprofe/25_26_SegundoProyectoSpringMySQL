package com.example.SegundoProyectoSpringMySQL.controllers;

import com.example.SegundoProyectoSpringMySQL.entities.Categoria;
import com.example.SegundoProyectoSpringMySQL.entities.Mensaje;
import com.example.SegundoProyectoSpringMySQL.repositories.CategoriaRepository;
import com.example.SegundoProyectoSpringMySQL.repositories.MensajeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
public class MensajeController {

    //@Autowired no es necesario indicar aquí esta anotación poque los hemos puesto como parámetros del constructor
    MensajeRepository mensajeRepository;
    CategoriaRepository categoriaRepository;

    //Inyectamos los repositorios en el constructor, así Spring los crea y gestiona automáticamente).
    public MensajeController(CategoriaRepository categoriaRepository, MensajeRepository mensajeRepository){

        //Para poder acceder desde otros métodos lo asigno a la propiedad de la clase
        this.mensajeRepository = mensajeRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/mensajes")
    public ResponseEntity<List<Mensaje>> findAllMensajes(){
        List<Mensaje> mensajes = mensajeRepository.findAll();
        if (mensajes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content si no hay mensajes
        }

        return ResponseEntity.ok(mensajes); //200 OK con todos los mensajes en el body
    }

    @GetMapping("/mensajes/{id}")
    public ResponseEntity<Mensaje> findMensajes(@PathVariable Long id){

        //Obtendo el mensaje de la BD a partir del repositorio
        Optional<Mensaje> mensajeOptional = mensajeRepository.findById(id);
//        if(mensajeOptional.isPresent()){
//            return ResponseEntity.ok(mensajeOptional.get());
//        }
//        else{   //No se ha encontrado el mensaje
//           return ResponseEntity.notFound().build();
//        }

        //.map se ejecuta si se ha encontrado el mensaje
        //.orElseGet se ejecuta si no ha encontrado el mensaje
        return mensajeOptional
                .map(ResponseEntity::ok)//OK 200
                .orElseGet(() -> ResponseEntity.notFound().build()); //Not Found 404

    }

    @DeleteMapping("/mensajes/{id}")
    public ResponseEntity<Void> removeMensajes(@PathVariable Long id){
        if(!mensajeRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); //Not Found 404
        }
        mensajeRepository.deleteById(id);
        return ResponseEntity.noContent().build(); //No content 204
    }

    @PutMapping("/mensajes/{id}")
    public ResponseEntity<Mensaje> editMensajes(@RequestBody Mensaje mensaje, @PathVariable Long id){
        return mensajeRepository.findById(id)
                .map(mensajeOriginal -> {
                    mensajeOriginal.setTitulo(mensaje.getTitulo());
                    mensajeOriginal.setTexto(mensaje.getTexto());
                    mensajeOriginal.setCategoria(mensaje.getCategoria());
                    return ResponseEntity.ok(mensajeRepository.save(mensajeOriginal));
                })
                .orElseGet(() -> {
                    return ResponseEntity.notFound().build();
                });
    }

    //POST localhost:8080/mensajes con el mensaje en json en el body
    //Debe devolver la URI donde se encuentra el mensaje en la cabecera Location
    @PostMapping("/mensajes")
    public ResponseEntity<Void> insertMensajes(@RequestBody Mensaje mensaje){
        Mensaje mensajeGuardado = mensajeRepository.save(mensaje);
        URI uriMensaje = URI.create("/mensajes/"+mensajeGuardado.getId());
        return ResponseEntity.created(uriMensaje).build();
    }
}


//CategoriaController incluyendo añadir y quitar mensaje de la categoria
//de la forma POST /categoria/5/mensaje/1 y DELETE /categoria/5/mensaje/1
//Crear comentarios a los mensajes
//Crear usuarios y likes a los mensajes