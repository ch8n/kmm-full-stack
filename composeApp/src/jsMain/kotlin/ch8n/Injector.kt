package ch8n

import networkModule
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module
import useCaseModule
import viewModelModule

@OptIn(ExperimentalJsExport::class)
@JsExport
object Injector : KoinComponent {

    fun test(): String = "Hello from Kotlin Singleton!"

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

    //val inputScreenViewModel by inject<LoginViewModel>()
}

val kotlinJsModule = module { }
