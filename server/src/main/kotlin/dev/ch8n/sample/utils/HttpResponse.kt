package dev.ch8n.sample.utils

import data.models.network.HttpState
import data.models.network.Response
import io.ktor.http.HttpStatusCode

/**
 * Represents HTTP response which will be exposed via API.
 */
sealed class HttpResponse<T> {
    abstract val code: HttpStatusCode
    abstract val body: Response<T>

    data class Ok<T>(
        override val code: HttpStatusCode = HttpStatusCode.OK,
        override val body: Response<T>
    ) : HttpResponse<T>()

    data class NotFound<T>(
        override val code: HttpStatusCode = HttpStatusCode.NotFound,
        override val body: Response<T>
    ) : HttpResponse<T>()

    data class BadRequest<T>(
        override val code: HttpStatusCode = HttpStatusCode.BadRequest,
        override val body: Response<T>
    ) : HttpResponse<T>()

    data class Unauthorized<T>(
        override val code: HttpStatusCode = HttpStatusCode.Unauthorized,
        override val body: Response<T>
    ) : HttpResponse<T>()

    companion object {
        fun <T> ok(response: Response<T>) = Ok(body = response)

        fun <T> notFound(response: Response<T>) = NotFound(body = response)

        fun <T> badRequest(response: Response<T>) = BadRequest(body = response)

        fun <T> unauth(response: Response<T>) = Unauthorized(body = response)
    }
}

/**
 * Generates [HttpResponse] from [Response<T>].
 */
fun <T> generateHttpResponse(response: Response<T>): HttpResponse<T> {
    return when (response.httpState) {
        HttpState.SUCCESS -> HttpResponse.ok(response)
        HttpState.NOT_FOUND -> HttpResponse.notFound(response)
        HttpState.FAILED -> HttpResponse.badRequest(response)
        HttpState.UNAUTHORIZED -> HttpResponse.unauth(response)
    }
}