package dev.ch8n.sample.data.local.database

import dev.ch8n.sample.data.local.database.tables.CollectionTable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

class CollectionDB {
    fun connectDB(): Database {
        val database = Database.connect("jdbc:sqlite:collection.sqlite", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        val tables = arrayOf(CollectionTable)
        transaction { SchemaUtils.createMissingTablesAndColumns(*tables) }
        return database
    }
}
suspend fun <T> dbQuery(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend () -> T
): T {
    return newSuspendedTransaction(dispatcher) { block.invoke() }
}