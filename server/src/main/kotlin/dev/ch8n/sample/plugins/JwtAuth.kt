package dev.ch8n.sample.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.ktor.ext.inject

data class JwtConfig(
    val issuer: String,
    val audience: String,
    val expiresIn: Long,
    val secret: String,
    val realm: String,
)

fun Application.configureJwtAuth() {
    val jwtConfig by inject<JwtConfig>()
    install(Authentication) {
        jwt {
            realm = jwtConfig.realm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtConfig.secret))
                    .withAudience(jwtConfig.audience)
                    .withIssuer(jwtConfig.issuer)
                    .build()
            )
            validate { jwtCreds ->
                if (jwtCreds.payload.audience.contains(jwtConfig.audience)) {
                    JWTPrincipal(jwtCreds.payload)
                } else {
                    null
                }
            }
        }
    }
}
