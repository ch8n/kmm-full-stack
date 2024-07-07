package dev.ch8n.sample.data.local.entities

import data.models.domain.Collection
import dev.ch8n.sample.data.local.database.tables.CollectionTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CollectionEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    
    companion object : UUIDEntityClass<CollectionEntity>(CollectionTable) {
        fun CollectionEntity.toDomain() = Collection(
            id = id.value.toString(),
            title = title,
            description = description,
            imageIdentifier = imageIdentifier
        )
    }

    var title by CollectionTable.title
    var description by CollectionTable.description
    var imageIdentifier by CollectionTable.imageIdentifier
}