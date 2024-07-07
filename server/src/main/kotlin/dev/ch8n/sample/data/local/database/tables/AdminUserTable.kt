package dev.ch8n.sample.data.local.database.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object AdminUserTable : UUIDTable() {
    val userName = text("userName").uniqueIndex()
    val password = text("password")
}