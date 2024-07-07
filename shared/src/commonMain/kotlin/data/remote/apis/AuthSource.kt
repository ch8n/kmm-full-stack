package data.remote.apis

import data.models.network.AuthRequest
import data.remote.ApiManager
import data.remote.Apis
import kotlin.js.JsName

@JsName("AuthSource")
class AuthSource(
    private val apiManager: ApiManager
) {
    suspend fun loginAdmin(
        authRequest: AuthRequest
    ) = apiManager.postRequest<AuthRequest, String?>(
        url = "${Apis.BASE_URL}/${Apis.AuthRoutes.ADMIN_LOGIN}",
        body = authRequest
    )
}