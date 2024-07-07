package dev.ch8n.sample.plugins

import dev.ch8n.sample.utils.BadRequestException
import dev.ch8n.sample.utils.ErrorMessages
import data.models.network.FailureResponse
import data.models.network.HttpState
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {

        exception<Throwable> { call, cause ->
            when (cause) {
                is BadRequestException -> call.respond(HttpStatusCode.BadRequest, cause)

                else -> call.respond(
                    HttpStatusCode.InternalServerError,
                    FailureResponse(HttpState.FAILED, buildString {
                        append(ErrorMessages.SOMETHING_WENT_WRONG.message)
                        append(" cause:${cause.message}")
                    })
                )
            }
        }

        status(HttpStatusCode.InternalServerError) { call, status ->
            call.respond(status, FailureResponse(HttpState.FAILED, ErrorMessages.SOMETHING_WENT_WRONG.message))
        }

        status(HttpStatusCode.Unauthorized) { call, status ->
            call.respond(status, FailureResponse(HttpState.FAILED, ErrorMessages.UNAUTHORIZED.message))
        }

        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(status, FailureResponse(HttpState.FAILED, ErrorMessages.NOT_FOUND.message))
        }
    }
}