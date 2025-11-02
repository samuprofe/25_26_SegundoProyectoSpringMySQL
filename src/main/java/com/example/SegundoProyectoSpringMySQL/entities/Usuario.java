package com.example.SegundoProyectoSpringMySQL.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter @Getter @Builder @AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellidos;

    private String roles; // "ROLE_USER,ROLE_ADMIN"

    @NotBlank (message = "El email no puede estar en blanco")
    @Column(unique = true)
    @Email (message = "El email no tiene el formato adecuado")
    private String email;


    @NotBlank
    @Size(min = 3)
    private String password;

    //Devuelve el listado de roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convierte los roles de String a GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : roles.split(",")) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


//¿Porqué si se reinicia el servidor dejan de funcionar los token?
//¿Dónde guardar los tokens en el cliente? ¿Cómo se cierra sesión?
//Quien puede insertar, listar, ver y borrar mensajes? (borrar admin y el propio usuario)
//¿Qué pasa si nos registramos con el mismo email 2 veces?
//Añadir relación entre usuarios y mensajes. Al insertar una reserva que se asigne el id del usuario conectado //¿Guardar el id en el token?