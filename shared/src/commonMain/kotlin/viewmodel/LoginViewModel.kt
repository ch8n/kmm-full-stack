package viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import usecases.LoginUser
import utils.ResultOf
import kotlin.js.JsName


@JsName("LoginViewModel")
class LoginViewModel(
    private val loginUser: LoginUser
) : ViewModel() {

    private val _authToken = MutableStateFlow("")
    val authToken = _authToken.asStateFlow()

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            val result = loginUser.invoke(
                userName = userName,
                password = password
            )
            when (result) {
                is ResultOf.Error -> {
                    _authToken.update { result.message ?: "" }
                }

                is ResultOf.Success -> {
                    _authToken.update { result.value.data ?: "" }
                }
            }
        }
    }

}