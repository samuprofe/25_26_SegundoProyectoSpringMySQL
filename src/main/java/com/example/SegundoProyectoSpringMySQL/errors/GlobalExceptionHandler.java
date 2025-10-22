package com.example.SegundoProyectoSpringMySQL.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice   //Controlador que va a manejar excepciones
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> manejadorValidaciones(MethodArgumentNotValidException ex){
        Map<String,String> map = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((error)->{
            map.put(error.getField(),error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(map);
    }

}
