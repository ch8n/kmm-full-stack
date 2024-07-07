package dev.ch8n.sample.utils

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

suspend inline fun <reified T : Any> RoutingContext.receiveOrBadRequest() = try {
    call.receive<T>()
} catch (e: Exception) {
    throw BadRequestException(ErrorMessages.MISSING_REQUEST_PARAMS.message)
}

fun RoutingContext.paramOrBadRequest(key: String) = try {
    call.parameters[key] ?: throw BadRequestException(ErrorMessages.MISSING_REQUEST_PARAMS.message)
} catch (e: Exception) {
    throw BadRequestException(ErrorMessages.MISSING_REQUEST_PARAMS.message)
}