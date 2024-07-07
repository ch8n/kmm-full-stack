package dev.ch8n.sample.data.local.entities

import data.models.domain.AdminUser
import dev.ch8n.sample.data.local.database.tables.AdminUserTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class AdminEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AdminEntity>(AdminUserTable) {
        fun AdminEntity.toDomain(): AdminUser {
            return AdminUser(
                adminId = id.toString(),
                userName = userName,
                password = password
            )
        }
    }

    var userName by AdminUserTable.userName
    var password by AdminUserTable.password
}

