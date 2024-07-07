package dev.ch8n.sample.routes.auth

import data.remote.Apis
import dev.ch8n.sample.utils.generateHttpResponse
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.authApis() {

    val authController by inject<AuthController>()
    val apiV1 = Apis::apiV1
    val authRoutes = Apis.AuthRoutes

    post(apiV1(authRoutes.ADMIN_LOGIN)) {
        val response = authController.loginAdmin(this)
        val httpResponse = generateHttpResponse(response)
        call.respond(httpResponse.code, httpResponse.body)
    }

    post(apiV1(authRoutes.ADMIN_CREATE)) {
        val response = authController.createAdmin(this)
        val httpResponse = generateHttpResponse(response)
        call.respond(httpResponse.code, httpResponse.body)
    }

    authenticate {
        get(apiV1(authRoutes.ADMIN_CHECK)) {
            val principal = call.principal<JWTPrincipal>()
            val admin = principal?.getClaim("adminId", String::class)
            call.respondText(status = HttpStatusCode.OK) {
                "Auth test passed! your id $admin"
            }
        }
    }

}