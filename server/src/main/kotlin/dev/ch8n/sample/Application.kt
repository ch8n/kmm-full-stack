package dev.ch8n.sample

import dev.ch8n.sample.plugins.*
import dev.ch8n.sample.routes.configureMainRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoinDI()
    configureExposedDB()
    configureSerialization()
    configCORS()
    configureStatusPages()
    configureJwtAuth()
    configureMainRouting()
}

