package com.api_crud_aplicacion.NIBAVISION

// Importaciones necesarias para que las pruebas y Spring funcionen
import com.api_crud_aplicacion.NIBAVISION.config.SecurityConfig
import com.api_crud_aplicacion.NIBAVISION.controllers.AuthController
import com.api_crud_aplicacion.NIBAVISION.models.User
import com.api_crud_aplicacion.NIBAVISION.models.dto.LoginRequest
import com.api_crud_aplicacion.NIBAVISION.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.Optional

/**
 * Clase de pruebas para el AuthController.
 * * Usamos @WebMvcTest en lugar de @SpringBootTest para hacer una prueba "ligera".
 * Esto significa que NO levantamos toda la aplicación ni la base de datos real,
 * solo cargamos la parte que maneja las peticiones web (los Controladores).
 * Le decimos específicamente que queremos probar el 'AuthController'.
 */
@WebMvcTest(AuthController::class)
// @Import: Como no cargamos toda la app, traemos manualmente la configuración de seguridad
// (SecurityConfig) para que el sistema sepa cómo encriptar contraseñas durante el test.
@Import(SecurityConfig::class)
class NibavisionApplicationTests {

    // @Autowired: Le pedimos a Spring que nos "inyecte" (nos dé) esta herramienta lista para usar.
    // MockMvc es como tener un Postman dentro del código. Nos permite simular peticiones HTTP.
    @Autowired
    private lateinit var mockMvc: MockMvc

    // ObjectMapper es una herramienta útil para convertir nuestros objetos de Kotlin (clases)
    // a texto JSON, que es lo que se envía en las peticiones web.
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    // Necesitamos el encriptador de contraseñas para simular correctamente el proceso de login/registro.
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    // @MockitoBean: Aquí ocurre la magia para evitar usar MySQL.
    // Creamos un "doble" o "simulacro" (Mock) del repositorio de usuarios.
    // Cuando el Controlador intente guardar o buscar en la base de datos, usará este objeto falso.
    // Nosotros controlaremos manualmente qué responde este objeto.
    @MockitoBean
    private lateinit var userRepository: UserRepository

    // --- PRUEBA 1: REGISTRO DE USUARIO ---
    @Test // Indica que esta función es una prueba ejecutable
    fun `registerUser devuelve 201 Created cuando el registro es exitoso`() {
        // 1. PREPARACIÓN (GIVEN)
        // Creamos los datos que vamos a enviar, como si un usuario llenara el formulario.
        val registroJson = mapOf(
            "fullName" to "Alumno Duoc",
            "email" to "alumno@duoc.cl",
            "password" to "Password123!",
            "phone" to "12345678"
        )

        // Entrenamos a nuestro repositorio falso (Mock):
        // "Oye, si te preguntan por este email, responde que está vacío (Optional.empty())",
        // simulando que el usuario NO existe todavía.
        `when`(userRepository.findByEmail("alumno@duoc.cl")).thenReturn(Optional.empty())

        // "Y si te piden guardar cualquier usuario (User class), responde devolviendo un usuario vacío",
        // simulando que el guardado fue exitoso sin errores.
        `when`(userRepository.save(any(User::class.java))).thenReturn(User())

        // 2. EJECUCIÓN (WHEN)
        // Usamos MockMvc para simular una petición POST real hacia nuestra API.
        mockMvc.perform(post("/api/auth/register") // Hacemos POST a esta ruta
            .contentType(MediaType.APPLICATION_JSON) // Avisamos que enviamos datos JSON
            .content(objectMapper.writeValueAsString(registroJson))) // Convertimos el mapa de datos a texto JSON

            // 3. VERIFICACIÓN (THEN)
            // Aquí revisamos si la respuesta es la que esperábamos.
            .andExpect(status().isCreated) // Esperamos un código HTTP 201 (Created)
            .andExpect(content().string("Usuario registrado exitosamente")) // Esperamos este mensaje exacto
    }

    // --- PRUEBA 2: LOGIN DE USUARIO ---
    @Test
    fun `loginUser devuelve 200 OK y datos del usuario cuando credenciales son validas`() {
        // 1. PREPARACIÓN (GIVEN)
        val rawPassword = "Password123!"
        // Importante: En la base de datos real las claves están encriptadas.
        // Así que encriptamos la clave de prueba para simular lo que habría guardado en la BD.
        val encodedPassword = passwordEncoder.encode(rawPassword)

        // Creamos un usuario ficticio que "ya existe" en la base de datos.
        val existingUser = User(
            id = 1,
            email = "profe@duoc.cl",
            password = encodedPassword, // Guardamos la clave ya encriptada
            fullName = "Profesor Test"
        )

        // Entrenamos al Mock: "Si buscan al profe@duoc.cl, devuelve este usuario que acabo de crear".
        `when`(userRepository.findByEmail("profe@duoc.cl")).thenReturn(Optional.of(existingUser))

        // Preparamos los datos que enviaría el usuario desde el Login (clave sin encriptar).
        val loginRequest = LoginRequest(email = "profe@duoc.cl", password = rawPassword)

        // 2. EJECUCIÓN (WHEN)
        // Simulamos el POST al login
        mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)))

            // 3. VERIFICACIÓN (THEN)
            .andExpect(status().isOk) // Esperamos código 200 (Éxito)
            // jsonPath nos permite inspeccionar el JSON que nos devolvió la API.
            // Verificamos que el email y el nombre en la respuesta sean los correctos.
            .andExpect(jsonPath("$.email").value("profe@duoc.cl"))
            .andExpect(jsonPath("$.fullName").value("Profesor Test"))
    }
}