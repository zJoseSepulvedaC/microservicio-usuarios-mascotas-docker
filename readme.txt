# 🐾 Microservicio de Usuarios y Mascotas
# Auto Jose Ignacio Sepúlveda C
# Ingeniería en Desarrollo de Software

Este microservicio, desarrollado con Spring Boot, gestiona usuarios asociados a una aplicación de transporte pet-friendly. Permite registrar usuarios, iniciar sesión, consultar por roles y realizar operaciones CRUD básicas.

## 🛠 Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Oracle Cloud (Base de Datos)
- Docker
- Maven
- HATEOAS
- JUnit (pruebas unitarias)

## 📂 Endpoints disponibles

| Método | Endpoint                        | Descripción                                   |
|--------|----------------------------------|-----------------------------------------------|
| GET    | `/usuarios`                     | Lista todos los usuarios (con HATEOAS)        |
| GET    | `/usuarios/{id}`                | Obtiene un usuario por ID                     |
| GET    | `/usuarios/rol/{rol}`           | Filtra usuarios por rol                       |
| GET    | `/usuarios/login`               | Autenticación de usuario por email y contraseña |
| GET    | `/usuarios/login/mensaje`       | Devuelve mensaje de login con éxito o fallo   |
| POST   | `/usuarios/registrar`           | Registra un nuevo usuario                     |
| PUT    | `/usuarios/{id}`                | Actualiza datos del usuario                   |
| DELETE | `/usuarios/{id}`                | Elimina un usuario                            |

## 🔗 Conexión a la Base de Datos

Este microservicio está conectado a una base de datos Oracle en Oracle Cloud. El acceso se realiza a través de un wallet configurado en el contenedor Docker.

## 🧪 Pruebas

- Pruebas de endpoints realizadas con Postman
- 2 pruebas unitarias con JUnit incluidas
- Uso de HATEOAS para enriquecer las respuestas con enlaces navegables

## 🚀 Despliegue en Docker

1. Empaquetar la app:
   ```bash
   mvn clean package

2. Construir imagen Docker:

docker build -t microservicio-usuarios .

3. Ejecutar Contenedor: 

docker run -p 8080:8080 microservicio-usuarios


