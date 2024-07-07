package data.models.network

import kotlinx.serialization.Serializable

interface Response<T> {
    val httpState: HttpState
    val message: String
    val data: T

    companion object {
        fun <T> success(
            data: T,
            httpState: HttpState = HttpState.SUCCESS,
            message: String = ""
        ): Response<T> {
            return object : Response<T> {
                override val httpState: HttpState
                    get() = httpState
                override val message: String
                    get() = message
                override val data: T
                    get() = data
            }
        }

        fun <T> error(message: String): Response<T?> {
            return object : Response<T?> {
                override val httpState: HttpState
                    get() = HttpState.FAILED
                override val message: String
                    get() = message
                override val data: T?
                    get() = null
            }
        }
    }
}

@Serializable
data class FailureResponse(
    override val httpState: HttpState,
    override val message: String,
    override val data: Unit = Unit
) : Response<Unit>

@Serializable
enum class HttpState {
    SUCCESS,
    NOT_FOUND,
    FAILED,
    UNAUTHORIZED
}
