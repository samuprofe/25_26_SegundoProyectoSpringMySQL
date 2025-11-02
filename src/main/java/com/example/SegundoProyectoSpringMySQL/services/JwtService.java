package com.example.SegundoProyectoSpringMySQL.services;

import com.example.SegundoProyectoSpringMySQL.entities.Usuario;
import com.example.SegundoProyectoSpringMySQL.repositories.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Getter
    private final SecretKey secretKey;

    public JwtService() {
        // Genera automáticamente una clave secreta segura de 256 bits
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    // Genera un token JWT para un usuario autenticado
    public String generateToken(Authentication authentication) {
        // Extraer los roles del usuario y convertirlos a String
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Usuario usuario = usuarioRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        // Construir el token JWT
        return Jwts.builder()
                .subject(authentication.getName())  // Email del usuario
                .issuer("gestion-centro-api")       // Quién emite el token
                .issuedAt(new Date())               // Cuándo se creó
                .expiration(new Date(
                        System.currentTimeMillis() + 86400000  // Expira en 24h
                ))
                .claim("roles", roles)              // Roles del usuario
                .claim("id",usuario.getId())
                .signWith(secretKey)                // Firmar con clave secreta
                .compact();                         // Generar String del token
    }

}