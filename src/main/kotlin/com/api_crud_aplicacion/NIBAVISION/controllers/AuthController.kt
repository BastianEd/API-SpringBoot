package com.api_crud_aplicacion.NIBAVISION.controllers

import com.api_crud_aplicacion.NIBAVISION.models.User
import com.api_crud_aplicacion.NIBAVISION.models.dto.LoginRequest
import com.api_crud_aplicacion.NIBAVISION.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse

@RestController
@RequestMapping("/api/auth") // URL base: http://localhost:8080/api/auth
class AuthController {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    // --- CREATE (Registro) ---
    // POST /api/auth/register
    @Operation(summary = "Registra un nuevo usuario", description = "Crea un nuevo usuario y hashea su contraseña.")
    @ApiResponse(responseCode = "201", description = "Usuario registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Error: El correo ya está en uso")
    @PostMapping("/register")
    fun registerUser(@RequestBody user: User): ResponseEntity<String> {
        // 1. Verificar si el email ya existe
        if (userRepository.findByEmail(user.email!!).isPresent) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error: El correo ya está en uso.")
        }

        // 2. Hashear la contraseña antes de guardarla
        user.password = passwordEncoder.encode(user.password)

        // 3. Guardar el usuario
        userRepository.save(user)

        // Tu app Android llamará a esto desde RegisterViewModel
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente")
    }

    // --- READ (Login) ---
    // POST /api/auth/login
    @PostMapping("/login")
    fun loginUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<*> {

        // 1. Buscar al usuario por email
        val userOptional = userRepository.findByEmail(loginRequest.email)

        if (userOptional.isEmpty) {
            // No encontramos al usuario
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas")
        }

        val user = userOptional.get()

        // 2. Verificar la contraseña hasheada
        if (passwordEncoder.matches(loginRequest.password, user.password)) {
            // ¡Contraseña correcta!
            // Devolvemos el objeto User (sin la contraseña, gracias a la anotación)
            return ResponseEntity.ok(user)
        } else {
            // Contraseña incorrecta
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas")
        }
    }
}