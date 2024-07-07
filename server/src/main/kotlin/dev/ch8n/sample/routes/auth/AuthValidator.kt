package dev.ch8n.sample.routes.auth

import data.models.network.AuthRequest
import dev.ch8n.sample.utils.BadRequestException

fun checkAuthRequest(authRequest: AuthRequest) = with(authRequest) {
    userName.ifEmpty { throw BadRequestException("UserName Empty!") }
    password.ifEmpty { throw BadRequestException("Password Empty!") }
    if (password.length > 8) {
        throw BadRequestException("Password less than 8 Chars!")
    }
    if (password.any { it.code in 'A'.code..'Z'.code }) {
        throw BadRequestException("Password must contain Captial Alphabet!")
    }
    if (password.any { it.code in '0'.code..'9'.code }) {
        throw BadRequestException("Password must contain number!")
    }
}