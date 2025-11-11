package com.api_crud_aplicacion.NIBAVISION.models

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    var fullName: String? = null,

    @Column(unique = true) // El email debe ser único
    var email: String? = null,

    // IMPORTANTE: Esta anotación oculta la contraseña en las respuestas JSON
    // El cliente la puede enviar, pero el servidor NUNCA la devolverá
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    var password: String? = null,

    var phone: String? = null,
    var address: String? = null,
    var profilePictureUri: String? = null,
    var favoriteGenres: String? = null // Guardamos como "FICCION,TERROR"
)