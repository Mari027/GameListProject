# GameList 🎮

Aplicación web para la gestión personal de videojuegos a modo de diario similar a aplicaciones como GoodReads. 
Permite a los usuarios crear una cuenta, gestionar su biblioteca personal de juegos y explorar un 
catálogo de títulos obtenido desde una API externa, en este caso RAWG.io.

**🌐 Aplicación desplegada:** https://game-list-project-green.vercel.app/  
**📁 Repositorio:** https://github.com/Mari027/GameListProject.git

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
- MariaDB 12
- Angular CLI 21

---

## Instalación y Ejecución

### Backend

1. Clona el repositorio
2. Crea la base de datos en MariaDB:
```sql
CREATE DATABASE gamelist;
```
3. Crea el archivo `application.properties` en `src/main/resources/` 
   basándote en el archivo `application.properties.example` incluido 
   en el repositorio y rellena tus credenciales
4. Arranca el backend desde IntelliJ o con:
```bash
./mvnw spring-boot:run
```

El backend estará disponible en `http://localhost:8080`  
Swagger UI disponible en `http://localhost:8080/swagger-ui/index.html`

### Frontend

1. Accede a la carpeta del frontend:
```bash
cd GL-frontend
```
2. Instala las dependencias:
```bash
npm install --legacy-peer-deps
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

- Registro e inicio de sesión con JWT y cierre de sesión automático al expirar el token
- Catálogo de videojuegos con búsqueda y paginación
- Biblioteca personal con estados, ratings, reseñas y filtro por estado
- Vista de detalle de juego con información de la API y datos personales del usuario
- Creación de juegos personalizados cuando no existen en el catálogo
- Importación y exportación de la biblioteca en formato CSV
- Panel de administración para gestión de usuarios
- Diseño responsive
- Notificaciones visuales con Toastr para feedback de las diferentes acciones dentro de la aplicación

---

## Créditos y Atribución

### RAWG Video Games Database API

Los datos del catálogo de videojuegos son proporcionados por 
**[RAWG Video Games Database](https://rawg.io/)**.

> Game data powered by [RAWG](https://rawg.io/apidocs)

RAWG es la base de datos de videojuegos más grande del mundo. Este proyecto 
utiliza su API pública para mostrar información sobre videojuegos, incluyendo 
títulos, imágenes y descripciones.

Para más información sobre la API de RAWG, visita: https://rawg.io/apidocs

### Uso de imágenes

Las imágenes de portada de los videojuegos mostradas en el catálogo y en la 
biblioteca son servidas directamente desde los servidores de RAWG a través 
de su API pública. Este proyecto no almacena ni redistribuye dichas imágenes, 
únicamente referencia las URLs proporcionadas por la API.

Este proyecto es de carácter **académico y no lucrativo**, desarrollado como 
trabajo de fin de ciclo del Grado Superior de Desarrollo de Aplicaciones Web. 
No se obtiene ningún beneficio económico de su uso ni se explotan 
comercialmente los contenidos de terceros.

Las imágenes de interfaz y fondos de pantalla utilizadas en la aplicación 
han sido creadas específicamente para este proyecto o son de uso libre. 
En caso de que algún recurso visual estuviese sujeto a derechos de autor, 
su uso queda amparado bajo el principio de **uso justo educativo** al 
tratarse de un proyecto sin fines comerciales.

---

## Licencia

Este proyecto ha sido desarrollado con fines académicos como trabajo para el módulo de Proyecto Intermodular 
del Ciclo Formativo de Grado Superior de Desarrollo de Aplicaciones Web.

---

## Autor

Desarrollado por **María Del Carmen Farfán**  
2º DAW B