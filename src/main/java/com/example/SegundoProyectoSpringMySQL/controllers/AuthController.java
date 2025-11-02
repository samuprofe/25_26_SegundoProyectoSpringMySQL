package com.example.SegundoProyectoSpringMySQL.controllers;

import com.example.SegundoProyectoSpringMySQL.DTO.LoginRequest;
import com.example.SegundoProyectoSpringMySQL.DTO.RegisterRequest;
import com.example.SegundoProyectoSpringMySQL.entities.Usuario;
import com.example.SegundoProyectoSpringMySQL.repositories.UsuarioRepository;
import com.example.SegundoProyectoSpringMySQL.services.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor    //Crea un constructor que Inyecta todos los objetos "final"
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar al usuario con email y password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );

            // Si las credenciales son correctas, generar token
            String token = jwtService.generateToken(authentication);
            return ResponseEntity.ok(Map.of("token", token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error en el servidor"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            // Verificar si el email ya existe
            if (usuarioRepository.findByEmail(registerRequest.email()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "El email ya está registrado"));
            }

            // Crear nuevo usuario
            Usuario usuario = new Usuario();
            usuario.setEmail(registerRequest.email());
            usuario.setPassword(passwordEncoder.encode(registerRequest.password()));  // Cifrar password
            usuario.setRoles("ROLE_USER");  // Rol por defecto

            usuarioRepository.save(usuario);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensaje", "Usuario registrado correctamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al registrar usuario"));
        }
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> getPerfil(@AuthenticationPrincipal Jwt jwt) {
        Long id = jwt.getClaim("id");
        String username = jwt.getSubject(); // El "subject" es el username (email normalmente)
        String roles = jwt.getClaim("roles");

        return ResponseEntity.ok(Map.of(
                "id", id,
                "username", username,
                "roles", roles
        ));
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        JwtAuthenticationToken token = (JwtAuthenticationToken) auth;
//
//        Jwt jwt = (Jwt) token.getPrincipal();
//
//        Long id = jwt.getClaim("id");
//        String username = jwt.getSubject();
//
//        return ResponseEntity.ok(Map.of(
//                "id", id,
//                "username", username
//        ));
    }

}
