package dev.ch8n.sample.data

import dev.ch8n.sample.data.local.database.CollectionDB
import dev.ch8n.sample.data.services.HashingService
import dev.ch8n.sample.data.services.ImageCompressionService
import dev.ch8n.sample.data.services.TokenService
import org.koin.dsl.module

val dataDIModule = module {
    single { ImageCompressionService() }
    single { CollectionDB() }
    single { HashingService() }
    single { TokenService() }
}