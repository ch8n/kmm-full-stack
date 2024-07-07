package dev.ch8n.sample.routes.auth

import data.models.network.AuthRequest
import data.models.network.HttpState
import data.models.network.Response
import dev.ch8n.sample.data.local.dao.AuthDAO
import dev.ch8n.sample.data.services.HashingService
import dev.ch8n.sample.data.services.TokenService
import dev.ch8n.sample.plugins.JwtConfig
import dev.ch8n.sample.utils.ErrorMessages
import dev.ch8n.sample.utils.receiveOrBadRequest
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

//https://github.dev/PatilShreyas/NotyKT/tree/master/noty-api
class AuthController(
    private val authDAO: AuthDAO,
    private val hashingService: HashingService,
    private val tokenService: TokenService,
    private val jwtConfig: JwtConfig,
) {
    suspend fun loginAdmin(routingContext: RoutingContext): Response<String?> {
        return try {
            val authRequest = routingContext.receiveOrBadRequest<AuthRequest>()
            checkAuthRequest(authRequest)
            val adminUser = authDAO.getAdmin(
                userName = authRequest.userName,
                passwordHash = hashingService.hash(authRequest.password)
            )
            val token = tokenService.generate(jwtConfig, "adminId" to adminUser.adminId)
            Response.success(token)
        } catch (e: Exception) {
            Response.error(
                message = buildString {
                    append("failed to login admin : ")
                    append(e.message)
                    appendLine("cause: ${e.cause?.message ?: ErrorMessages.SOMETHING_WENT_WRONG.message}")
                }
            )
        }
    }

    suspend fun createAdmin(
        routingContext: RoutingContext
    ): Response<String?> {
        return try {
            val authRequest = routingContext.receiveOrBadRequest<AuthRequest>()
            checkAuthRequest(authRequest)
            authDAO.createAdmin(
                userName = authRequest.userName,
                password = hashingService.hash(authRequest.password)
            )
            Response.success(
                httpState = HttpState.SUCCESS,
                message = "Admin created!",
                data = ""
            )
        } catch (e: Exception) {
            Response.error(
                message = buildString {
                    append("failed to create admin : ")
                    append(e.message)
                    appendLine("cause: ${e.cause?.message ?: ErrorMessages.SOMETHING_WENT_WRONG.message}")
                }
            )
        }

    }
}
