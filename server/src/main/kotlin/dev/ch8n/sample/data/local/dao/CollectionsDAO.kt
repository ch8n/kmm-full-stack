package dev.ch8n.sample.data.local.dao

import data.models.domain.Collection
import dev.ch8n.sample.data.local.database.dbQuery
import dev.ch8n.sample.data.local.database.tables.CollectionTable
import dev.ch8n.sample.data.local.entities.CollectionEntity
import dev.ch8n.sample.data.local.entities.CollectionEntity.Companion.toDomain
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class CollectionsDAO {

    suspend fun createCollection(
        title: String,
        description: String,
    ): Collection = dbQuery {
        val entity = CollectionEntity.new {
            this.title = title
            this.description = description
            this.imageIdentifier = ""
        }
        return@dbQuery entity.toDomain()
    }

    suspend fun getCollections(pageNumber: Int, pageSize: Int = 10): List<Collection> = dbQuery {
        CollectionTable.selectAll()
            .limit(pageSize, offset = pageNumber.toLong() * pageSize)
            .map {
                Collection(
                    id = it[CollectionTable.id].value.toString(),
                    title = it[CollectionTable.title],
                    description = it[CollectionTable.description],
                    imageIdentifier = it[CollectionTable.imageIdentifier]
                )
            }
    }

    suspend fun getCollection(collectionId: String): Collection = dbQuery {
        val collectionEntity = CollectionEntity.findById(UUID.fromString(collectionId))
            ?: throw NotFoundException("Collection with id not found...")
        return@dbQuery collectionEntity.toDomain()
    }

    suspend fun updateCollection(
        collectionId: String,
        title: String = "",
        description: String = "",
        imageIdentifier: String = ""
    ): Collection = dbQuery {
        val updatedEntitiy = CollectionEntity[UUID.fromString(collectionId)].apply {
            if (title.isNotEmpty()) this.title = title
            if (description.isNotEmpty()) this.description = description
            if (imageIdentifier.isNotEmpty()) this.imageIdentifier = imageIdentifier
        }
        return@dbQuery updatedEntitiy.toDomain()
    }
}