package com.example.SegundoProyectoSpringMySQL.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

//@Data
//@AllArgsConstructor
//public class MensajeRequest {
//    @NotBlank(message = "El título no puede estar en blanco")
//    @Size(min = 3, max = 100)
//    private String titulo;
//
//    @NotBlank(message = "El texto del mensaje no puede estar en blanco")
//    private String texto;
//
//    @NotNull
//    private Long categoria_id;
//}

public record MensajeRequest(
    @NotBlank(message = "El título no puede estar en blanco")
    @Size(min = 3, max = 100)
    String titulo,

    @NotBlank(message = "El texto del mensaje no puede estar en blanco")
    String texto,

    @NotNull
    Long categoria_id
)
{}


