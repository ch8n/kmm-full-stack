package dev.ch8n.sample.routes.collection

import dev.ch8n.sample.data.local.dao.CollectionsDAO
import org.koin.dsl.module

val collectionDIModule = module {
    single { CollectionsDAO() }
    single {
        CollectionController(
            collectionsDAO = get(),
            imageCompressionService = get()
        )
    }
}