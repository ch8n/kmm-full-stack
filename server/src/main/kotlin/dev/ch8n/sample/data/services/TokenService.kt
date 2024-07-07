package dev.ch8n.sample.data.services

import dev.ch8n.sample.plugins.JwtConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class TokenService() {
    fun generate(config: JwtConfig, claim: Pair<String, String>): String {
        return JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))
            .withClaim(claim.first, claim.second)
            .sign(Algorithm.HMAC256(config.secret))
    }
}
