package com.example.SegundoProyectoSpringMySQL.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe ser válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 3, message = "La contraseña debe tener al menos 3 caracteres")
        String password,

        String nombre,

        String apellidos
) {}