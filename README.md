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
    CREATE DATABASE zonalibros_db;
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