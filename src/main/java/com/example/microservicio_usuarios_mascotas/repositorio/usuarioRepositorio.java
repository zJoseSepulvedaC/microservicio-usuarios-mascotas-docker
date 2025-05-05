package com.example.microservicio_usuarios_mascotas.repositorio;

import com.example.microservicio_usuarios_mascotas.model.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface usuarioRepositorio extends JpaRepository<usuario, Long> {
    Optional<usuario> findByEmailAndContraseña(String email, String contraseña);
    Optional<usuario> findByEmail(String email); // 👈 AGREGA ESTA LÍNEA
    List<usuario> findByRol(String rol);
}

