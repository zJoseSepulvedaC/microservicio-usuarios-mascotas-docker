package com.example.microservicio_usuarios_mascotas;

import com.example.microservicio_usuarios_mascotas.model.usuario;
import com.example.microservicio_usuarios_mascotas.repositorio.usuarioRepositorio;
import com.example.microservicio_usuarios_mascotas.service.usuarioservice;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class usuarioserviceTest {

    @Mock
    private usuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private usuarioservice usuarioservice;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void registrarUsuarioCorrectamente() {
        // Arrange
        String nombre = "Carlos Pérez";
        String email = "carlos@example.com";
        String contraseña = "abc123";
        String rol = "user";

        when(usuarioRepositorio.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        String resultado = usuarioservice.registrar(nombre, email, contraseña, rol);

        // Assert
        assertEquals("Usuario registrado correctamente.", resultado);
        verify(usuarioRepositorio, times(1)).save(any(usuario.class));
    }

    @Test
    void registrarUsuarioConCamposVacios() {
        // Arrange
        String resultado = usuarioservice.registrar("", "", "", "");

        // Assert
        assertEquals("Todos los campos son obligatorios.", resultado);
        verify(usuarioRepositorio, never()).save(any(usuario.class));
    }

    @Test
    void loginUsuarioExistente() {
        // Arrange
        String email = "ana@example.com";
        String contraseña = "password123";
        usuario mockUsuario = new usuario(1L, "Ana Torres", email, contraseña, "user");

        when(usuarioRepositorio.findByEmailAndContraseña(email, contraseña))
                .thenReturn(Optional.of(mockUsuario));

        // Act
        Optional<usuario> resultado = usuarioservice.login(email, contraseña);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals("Ana Torres", resultado.get().getNombre());
        verify(usuarioRepositorio, times(1)).findByEmailAndContraseña(email, contraseña);
    }

}
