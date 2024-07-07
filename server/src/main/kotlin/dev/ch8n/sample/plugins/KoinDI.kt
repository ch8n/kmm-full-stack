package dev.ch8n.sample.plugins

import dev.ch8n.sample.data.dataDIModule
import dev.ch8n.sample.routes.auth.authDIModule
import dev.ch8n.sample.routes.collection.collectionDIModule
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureKoinDI() {
    install(Koin) {
        val jwtConfigModule = module {
            single {
                JwtConfig(
                    issuer = environment.config.property("jwt.issuer").getString(),
                    audience = environment.config.property("jwt.audience").getString(),
                    expiresIn = 30L * 1000L * 60L * 60L * 24L, // 30days
                    secret = environment.config.property("jwt.secret").getString(),
                    realm = environment.config.property("jwt.realm").getString()
                )
            }
        }
        modules(
            dataDIModule,
            collectionDIModule,
            jwtConfigModule,
            authDIModule,
        )

    }
}