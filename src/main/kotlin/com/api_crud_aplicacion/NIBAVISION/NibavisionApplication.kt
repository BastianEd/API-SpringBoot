package com.api_crud_aplicacion.NIBAVISION

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

// --- AÑADE ESTA ANOTACIÓN ---
@OpenAPIDefinition(
    info = Info(
        title = "ZONALIBROS API",
        version = "1.0",
        description = "API para la aplicación móvil Zonalibros (NIBA_Vision)"
    )
)
@SpringBootApplication
class NibavisionApplication

fun main(args: Array<String>) {
	runApplication<NibavisionApplication>(*args)
}
