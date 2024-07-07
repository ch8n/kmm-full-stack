package ch8n

import networkModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.dsl.module
import useCaseModule
import viewModelModule
import viewmodel.LoginViewModel

@OptIn(ExperimentalJsExport::class)
@JsExport
object Injector : KoinComponent {

    init {
        console.log("starting koin injection")
        startKoin {
            modules(
                networkModule,
                useCaseModule,
                viewModelModule,
                kotlinJsModule
            )
        }
    }

    val loginViewModel by inject<LoginViewModel>()
}

val kotlinJsModule = module { }
