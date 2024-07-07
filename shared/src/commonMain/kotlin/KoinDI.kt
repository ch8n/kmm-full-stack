import data.remote.ApiManager
import data.remote.apis.AuthSource
import io.ktor.client.*
import org.koin.dsl.module
import usecases.LoginUser
import viewmodel.LoginViewModel


val networkModule = module {
    single { HttpClient() }
    single { ApiManager(httpClient = get()) }
    single { AuthSource(apiManager = get()) }
}

val useCaseModule = module {
    single { LoginUser(authSource = get()) }
}

val viewModelModule = module {
    single { LoginViewModel(loginUser = get()) }
}

