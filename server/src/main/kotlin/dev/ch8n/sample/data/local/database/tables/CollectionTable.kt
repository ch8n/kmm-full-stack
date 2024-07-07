package dev.ch8n.sample.data.local.database.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object CollectionTable : UUIDTable() {
    val title = text("title")
    val description = text("description")
    val imageIdentifier = text("imageIdentifier")
}