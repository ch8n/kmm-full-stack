package data.remote

object Apis {

    const val BASE_URL = "http://0.0.0.0:8000"

    fun apiV1(path: String) = "/api/v1/$path"

    object AuthRoutes {
        const val ADMIN_LOGIN = "admin/login"
        const val ADMIN_CREATE = "admin/create"
        const val ADMIN_CHECK = "admin/check"
    }
}