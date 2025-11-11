package com.api_crud_aplicacion.NIBAVISION.config

import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.net.InetAddress

@Component
class SwaggerLogger(private val env: Environment) : ApplicationListener<ServletWebServerInitializedEvent> {

    // Obtenemos un logger para imprimir en la consola
    private val log = LoggerFactory.getLogger(SwaggerLogger::class.java)

    override fun onApplicationEvent(event: ServletWebServerInitializedEvent) {
        // 1. Obtener el puerto en el que está corriendo el servidor
        val port = event.webServer.port

        // 2. Obtener la dirección IP o "localhost"
        val host = InetAddress.getLocalHost().hostAddress

        // 3. Obtener el "context path" (si definiste uno en application.properties)
        // Por ej: server.servlet.context-path=/api
        val contextPath = env.getProperty("server.servlet.context-path") ?: "/"

        // 4. Construir las URLs
        val swaggerUrl = "http://$host:$port${contextPath}swagger-ui/index.html"
        val localUrl = "http://localhost:$port${contextPath}swagger-ui/index.html"

        // 5. Imprimir en la consola
        log.info("------------------------------------------------------------")
        log.info("  Swagger UI disponible en:")
        log.info("  Localhost: \t\t$localUrl")
        log.info("  IP: \t$swaggerUrl")
        log.info("------------------------------------------------------------")
    }
}