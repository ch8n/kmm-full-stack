package data.remote.apis

import data.models.network.AuthRequest
import data.remote.ApiManager
import data.remote.Apis

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