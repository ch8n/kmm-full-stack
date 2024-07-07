package dev.ch8n.sample.plugins

import dev.ch8n.sample.data.local.database.CollectionDB
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureExposedDB() {
    val collectionDB by inject<CollectionDB>()
    collectionDB.connectDB()
}

