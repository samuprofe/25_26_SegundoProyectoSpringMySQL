package com.example.SegundoProyectoSpringMySQL.repositories;

import com.example.SegundoProyectoSpringMySQL.entities.Categoria;
import com.example.SegundoProyectoSpringMySQL.entities.Mensaje;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje,Long> {
    public List<Mensaje> findByTitulo(String titulo);
    public List<Mensaje> findByFechaCreacionBetween(Date fechaCreacion, Date fechaActual);
}
