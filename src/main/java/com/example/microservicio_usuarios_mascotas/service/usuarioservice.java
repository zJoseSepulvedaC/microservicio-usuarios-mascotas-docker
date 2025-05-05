package com.example.microservicio_usuarios_mascotas.service;

import com.example.microservicio_usuarios_mascotas.model.usuario;
import com.example.microservicio_usuarios_mascotas.repositorio.usuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class usuarioservice {

    private final usuarioRepositorio usuarioRepositorio;

    public usuarioservice(usuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public List<usuario> obtenerTodos() {
        return usuarioRepositorio.findAll();
    }

    public Optional<usuario> obtenerPorId(Long id) {
        return usuarioRepositorio.findById(id);
    }

    public List<usuario> obtenerPorRol(String rol) {
        return usuarioRepositorio.findByRol(rol);
    }

    public Optional<usuario> login(String email, String contraseña) {
        return usuarioRepositorio.findByEmailAndContraseña(email, contraseña);
    }

    public String loginConMensaje(String email, String contraseña) {
        Optional<usuario> usuarioEncontrado = usuarioRepositorio.findByEmailAndContraseña(email, contraseña);
        if (usuarioEncontrado.isEmpty()) {
            return "Correo o contraseña incorrectos";
        }
        return "Bienvenido, " + usuarioEncontrado.get().getNombre() + " (" + usuarioEncontrado.get().getRol() + ")";
    }

    public String registrar(String nombre, String email, String contraseña, String rol) {
        if (nombre.isEmpty() || email.isEmpty() || contraseña.isEmpty() || rol.isEmpty()) {
            return "Todos los campos son obligatorios.";
        }
    
        // ✅ Verifica solo el correo
        if (usuarioRepositorio.findByEmail(email).isPresent()) {
            return "Ya existe un usuario con este email.";
        }
    
        usuario nuevoUsuario = new usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setContraseña(contraseña);
        nuevoUsuario.setRol(rol);
    
        usuarioRepositorio.save(nuevoUsuario);
    
        return "Usuario registrado correctamente.";
    }
    
    public String actualizarUsuario(Long id, usuario usuarioActualizado) {
        Optional<usuario> existente = usuarioRepositorio.findById(id);
        if (existente.isEmpty()) return "Usuario no encontrado";
    
        usuario u = existente.get();
        u.setNombre(usuarioActualizado.getNombre());
        u.setEmail(usuarioActualizado.getEmail());
        u.setContraseña(usuarioActualizado.getContraseña());
        u.setRol(usuarioActualizado.getRol());
        usuarioRepositorio.save(u);
    
        return "Usuario actualizado correctamente";
    }
    
    public String eliminarUsuario(Long id) {
        if (!usuarioRepositorio.existsById(id)) return "Usuario no encontrado";
        usuarioRepositorio.deleteById(id);
        return "Usuario eliminado correctamente";
    }
    

}
