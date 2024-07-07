package dev.ch8n.sample.routes.auth

import dev.ch8n.sample.data.local.dao.AuthDAO
import dev.ch8n.sample.plugins.JwtConfig
import org.koin.dsl.module

val authDIModule = module {
    single { AuthDAO() }
    single {
        AuthController(
            authDAO = get(),
            hashingService = get(),
            tokenService = get(),
            jwtConfig = get()
        )
    }
}