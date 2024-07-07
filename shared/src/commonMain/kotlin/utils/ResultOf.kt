package utils

sealed class ResultOf<out T> {
    data class Success<T>(val value: T) : ResultOf<T>()
    data class Error(
        val message: String?,
        val throwable: Throwable?
    ) : ResultOf<Nothing>()

    companion object {
        suspend fun <T> build(block: suspend () -> T): ResultOf<T> {
            return try {
                Success(block.invoke())
            } catch (e: Exception) {
                Error(e.message, e.cause)
            }
        }
    }
}