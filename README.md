# GameList 🎮

Aplicación web para la gestión personal de videojuegos. Permite a los usuarios 
crear una cuenta, gestionar su biblioteca personal de juegos y explorar un 
catálogo de títulos obtenido desde una API externa en este caso Rawg.io.

---

## Tecnologías Utilizadas

### Backend
- Java 17
- Spring Boot 4
- Spring Security + JWT
- Spring Data JPA
- MariaDB
- Maven

### Frontend
- Angular 21
- TypeScript
- SCSS

---

## Requisitos Previos

- Java 17 o superior
- Node.js 18 o superior
- npm 10 o superior
- MariaDB 10.6 o superior
- Angular CLI 21

---

## Instalación y Ejecución

### Backend

1. Clona el repositorio
2. Configura el archivo `application.properties` con tus credenciales
3. Crea la base de datos:
```sql
CREATE DATABASE gamelist;
```
4. Arranca el backend:
```bash
./mvnw spring-boot:run
```

### Frontend

1. Accede a la carpeta del frontend
2. Instala las dependencias:
```bash
npm install
```
3. Arranca el frontend:
```bash
ng serve
```

La aplicación estará disponible en `http://localhost:4200`

---

## Creación del Usuario Administrador

Regístrate normalmente desde `/register` y luego ejecuta en la base de datos:

```sql
UPDATE users SET role = 'ADMIN' WHERE email = 'tu_email@ejemplo.com';
```

---

## Funcionalidades Principales

- Registro e inicio de sesión con JWT
- Catálogo de videojuegos con búsqueda y paginación
- Biblioteca personal con estados, ratings y reseñas
- Creación de juegos personalizados
- Panel de administración

---

## Créditos y Atribución

### RAWG Video Games Database API

Los datos del catálogo de videojuegos son proporcionados por 
**[RAWG Video Games Database](https://rawg.io/)**.

> Game data powered by [RAWG](https://rawg.io/apidocs)

RAWG es la base de datos de videojuegos más grande del mundo. 
Este proyecto utiliza su API pública para mostrar información 
sobre videojuegos, incluyendo títulos, imágenes y descripciones.

Para más información sobre la API de RAWG, visita: https://rawg.io/apidocs

---

## Licencia

Este proyecto ha sido desarrollado con fines académicos.

---

## Autor

Desarrollado por [Tu Nombre]
