package com.example.SegundoProyectoSpringMySQL.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la categoría no puede estar en blanco")
    private String nombreCategoria;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private List<Mensaje> mensajes;
}
