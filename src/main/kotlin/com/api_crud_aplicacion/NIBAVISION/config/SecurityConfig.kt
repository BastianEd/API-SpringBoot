package com.api_crud_aplicacion.NIBAVISION.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        // Usamos BCrypt para encriptar las contraseñas
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.invoke {
            // Deshabilitamos CSRF (común para APIs REST)
            csrf { disable() }

            authorizeHttpRequests {
                // Permitimos que CUALQUIERA pueda acceder a los endpoints de:
                // Registro y Login
                authorize("/api/auth/**", permitAll)
                // Ver los libros
                authorize("/api/books/**", permitAll)

                // Permiten el acceso a la UI de Swagger y su definición JSON
                authorize("/swagger-ui/**", permitAll)
                authorize("/v3/api-docs/**", permitAll)

                // Cualquier otra petición (ej. /api/profile) requeriría autenticación
                authorize(anyRequest, authenticated)
            }
        }
        return http.build()
    }
}