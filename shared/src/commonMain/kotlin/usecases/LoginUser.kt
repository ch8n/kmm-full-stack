package usecases

import data.models.network.AuthRequest
import data.models.network.Response
import data.remote.apis.AuthSource
import utils.ResultOf
import kotlin.js.JsName

@JsName("LoginUser")
class LoginUser(
    private val authSource: AuthSource
) {
    suspend operator fun invoke(
        userName: String,
        password: String
    ): ResultOf<Response<String?>> {
        return authSource.loginAdmin(
            AuthRequest(
                userName = userName,
                password = password
            )
        )
    }
}
