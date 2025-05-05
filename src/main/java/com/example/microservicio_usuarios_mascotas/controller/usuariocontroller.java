package com.example.microservicio_usuarios_mascotas.controller;

import com.example.microservicio_usuarios_mascotas.model.usuario;
import com.example.microservicio_usuarios_mascotas.service.usuarioservice;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class usuariocontroller {

    private final usuarioservice usuarioservice;

    public usuariocontroller(usuarioservice usuarioservice) {
        this.usuarioservice = usuarioservice;
    }

    // Listar todos los usuarios con enlaces HATEOAS
    @GetMapping
    public CollectionModel<EntityModel<usuario>> obtenerTodos() {
        List<EntityModel<usuario>> usuarios = usuarioservice.obtenerTodos().stream()
            .map(u -> EntityModel.of(u,
                linkTo(methodOn(usuariocontroller.class).obtenerPorId(u.getId())).withSelfRel(),
                linkTo(methodOn(usuariocontroller.class).obtenerTodos()).withRel("usuarios")))
            .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
            linkTo(methodOn(usuariocontroller.class).obtenerTodos()).withSelfRel());
    }

    // Obtener un usuario específico con HATEOAS
    @GetMapping("/{id}")
    public EntityModel<usuario> obtenerPorId(@PathVariable Long id) {
        Optional<usuario> usuario = usuarioservice.obtenerPorId(id);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        return EntityModel.of(usuario.get(),
            linkTo(methodOn(usuariocontroller.class).obtenerPorId(id)).withSelfRel(),
            linkTo(methodOn(usuariocontroller.class).obtenerTodos()).withRel("usuarios"));
    }

    @GetMapping("/rol/{rol}")
    public List<usuario> obtenerPorRol(@PathVariable String rol) {
        return usuarioservice.obtenerPorRol(rol);
    }

    @GetMapping("/login")
    public Optional<usuario> login(@RequestParam String email, @RequestParam String contraseña) {
        return usuarioservice.login(email, contraseña);
    }

    @GetMapping("/login/mensaje")
    public String loginConMensaje(@RequestParam String email, @RequestParam String contraseña) {
        return usuarioservice.loginConMensaje(email, contraseña);
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@RequestBody usuario nuevoUsuario) {
        System.out.println("📩 Registro recibido: " + nuevoUsuario.getEmail());
        return usuarioservice.registrar(
            nuevoUsuario.getNombre(),
            nuevoUsuario.getEmail(),
            nuevoUsuario.getContraseña(),
            nuevoUsuario.getRol()
        );
    }

    @PutMapping("/{id}")
    public String actualizarUsuario(@PathVariable Long id, @RequestBody usuario usuarioActualizado) {
        return usuarioservice.actualizarUsuario(id, usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        return usuarioservice.eliminarUsuario(id);
    }
}
