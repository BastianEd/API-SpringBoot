# API de ZONALIBROS (NIBAVISION)

Este es el proyecto backend (API REST) para la aplicación móvil **ZONALIBROS (NIBAVISION)**. Está desarrollado con Spring Boot y Kotlin, y proporciona todos los servicios necesarios para la gestión de usuarios y libros.

La API está documentada y se puede probar interactivamente usando **Swagger (Springdoc)**.

---

## 🚀 Tecnologías Utilizadas

* **Framework:** Spring Boot 3.5.7
* **Lenguaje:** Kotlin
* **Base de Datos:** MySQL (gestionado con Laragon)
* **Acceso a Datos:** Spring Data JPA (Hibernate)
* **Seguridad:** Spring Security (para el hasheo de contraseñas con BCrypt)
* **Documentación:** Springdoc (Swagger UI)
* **Gestor de Dependencias:** Gradle (Groovy)

---

## ⚙️ Configuración y Puesta en Marcha

Sigue estos pasos para ejecutar el proyecto localmente.

### 1. Prerrequisitos

* Tener Laragon (o cualquier servidor MySQL) instalado y en ejecución.
* Tener un IDE compatible (como IntelliJ IDEA o VS Code con las extensiones de Java/Kotlin).
* Tener Java 17 o superior instalado.

### 2. Base de Datos (Laragon)

1.  Inicia Laragon y asegúrate de que el servicio de **MySQL** esté corriendo.
2.  Haz clic en el botón **"Database"** de Laragon (o abre tu gestor de BD preferido, como HeidiSQL o DBeaver).
3.  Crea una nueva base de datos. Para este proyecto, el nombre por defecto es **`zonalibros_db`**.

    ```sql
     -- Volcando estructura de base de datos para zonalibros_db
     CREATE DATABASE IF NOT EXISTS `zonalibros_db`;
     USE `zonalibros_db`;

     -- Volcando datos para la tabla zonalibros_db.books: ~2 rows (aproximadamente)
     INSERT INTO `books` (`id`, `author`, `cover_image_url`, `is_new`, `price`, `price_value`, `title`) VALUES
     (1, 'ROWLING, J.K.', 'https://i.pinimg.com/1200x/ee/23/df/ee23df3a67f6f27ab8645debc9f6d5e3.jpg', b'1', '$22.900', 22900, 'Harry Potter y la piedra filosofal'),
     (2, 'ROWLING, J.K.', 'https://i.pinimg.com/736x/fe/a1/c8/fea1c802f18a47c29e47a8fcc13e1e7f.jpg', b'1', '$23.500', 22900, 'Harry Potter y la cámara secreta'),
     (3, 'ROWLING, J.K.', 'https://i.pinimg.com/1200x/57/8c/f5/578cf5e990eaeb85ccdadf786006c6a7.jpg', b'1', '$24.900', 24900, 'Harry Potter y el prisionero de Azkaban'),
     (4, 'ROWLING, J.K.', 'https://i.pinimg.com/1200x/66/ca/68/66ca681acf564417ac238874ccff97d5.jpg', b'1', '$27.900', 27900, 'Harry Potter y el cáliz de fuego'),
     (5, 'ROWLING, J.K.', 'https://i.pinimg.com/736x/3f/b1/2c/3fb12c0230d31a576c7687ffe894de75.jpg', b'1', '$29.900', 29900, 'Harry Potter y la Orden del Fénix'),
     (6, 'ROWLING, J.K.', 'https://i.pinimg.com/736x/d5/b0/a9/d5b0a9bc8dd1b2b2ff462c820ac0f1a8.jpg', b'1', '$30.500', 30500, 'Harry Potter y el misterio del príncipe'),
     (7, 'ROWLING, J.K.', 'https://i.pinimg.com/736x/0c/82/5d/0c825d08fb10e3ee03472de41bae6183.jpg', b'1', '$32.000', 32000, 'Harry Potter y las Reliquias de la Muerte'),
     (8, 'ROWLING, J.K.', 'https://i.pinimg.com/736x/99/46/d8/9946d822a2e92707bdf3ca47d15ebed1.jpg', b'1', '$25.000', 25000, 'Harry Potter y el legado maldito');


     -- Volcando datos para la tabla zonalibros_db.users: ~0 rows (aproximadamente)
     INSERT INTO `users` (`id`, `address`, `email`, `favorite_genres`, `full_name`, `password`, `phone`, `profile_picture_uri`) VALUES
     (1, 'Direccion de Nicolas', 'nico.fo@nic.cl', 'string', 'Nicolas Fonseca', '$2a$10$Jk3sKSJHEmDwjZulsJnoSOGwVp91OQm9.mk587pANE0TaKkaz.uT6', '+56945673490', 'string'),
     (2, 'Dirección falsa 3', 'mangel@duoc.cl', 'FICCION,TERROR,SUSPENSO', 'intento tres', '$2a$10$M6s8jk3r8FXBD170dC12BOh56ppPzzndJSoC56jyhNacbGmHsXqGu', '+56956782345', 'content://com.example.niba_vision.provider/my_images/JPEG_20251112_184523_9131687754642596395.jpg');


    ```
    

### 3. Configurar la API

1.  Clona este repositorio en tu máquina.
2.  Abre el archivo: `src/main/resources/application.properties`.
3.  Asegúrate de que la configuración de la base de datos coincida con la tuya (Laragon usa `root` y sin contraseña por defecto):

    ```properties
    # Conexión a tu MySQL en Laragon
    spring.datasource.url=jdbc:mysql://localhost:3306/zonalibros_db
    spring.datasource.username=root
    spring.datasource.password=

    # Le dice a JPA (Hibernate) que cree o actualice las tablas basado en tus clases de Kotlin
    spring.jpa.hibernate.ddl-auto=update

    # Muestra el SQL que JPA ejecuta en la consola (útil para depurar)
    spring.jpa.show-sql=true
    ```

### 4. Ejecutar el Proyecto

1.  Abre el proyecto con tu IDE (IntelliJ IDEA es recomendado para proyectos Spring/Kotlin).
2.  Espera a que Gradle descargue todas las dependencias.
3.  Busca y ejecuta el archivo principal `NibavisionApplication.kt`.
4.  La API se iniciará y estará disponible en `http://localhost:8080`.

---

## API de Uso y Pruebas (Swagger)

La forma más fácil de probar todos los endpoints del CRUD es usando la interfaz de Swagger UI que hemos integrado.

Una vez que la API esté en ejecución, abre la siguiente URL en tu navegador:

**[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

Verás una interfaz gráfica donde podrás:
* Probar el registro (`/api/auth/register`) y el login (`/api/auth/login`).
* Probar el CRUD completo de Libros (`/api/books`):
    * `GET /api/books` (Obtener todos los libros)
    * `POST /api/books` (Crear un nuevo libro)
    * `GET /api/books/{id}` (Obtener un libro específico)
    * `PUT /api/books/{id}` (Actualizar un libro)
    * `DELETE /api/books/{id}` (Borrar un libro)

![Imagen de la interfaz de Swagger UI](https://i.ibb.co/NdDgm94h/Zona-Libro-API-Swagger.png)