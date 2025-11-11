package com.api_crud_aplicacion.NIBAVISION.models.dto

// Una data class simple para recibir el JSON del login
data class LoginRequest(
    val email: String,
    val password: String
)