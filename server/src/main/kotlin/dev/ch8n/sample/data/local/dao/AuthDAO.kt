package dev.ch8n.sample.data.local.dao

import data.models.domain.AdminUser
import dev.ch8n.sample.data.local.database.dbQuery
import dev.ch8n.sample.data.local.database.tables.AdminUserTable
import dev.ch8n.sample.data.local.entities.AdminEntity
import dev.ch8n.sample.data.local.entities.AdminEntity.Companion.toDomain
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and

class AuthDAO {

    suspend fun createAdmin(
        userName: String,
        password: String
    ): AdminUser = dbQuery {
        val entity = AdminEntity.new {
            this.userName = userName
            this.password = password
        }
        return@dbQuery entity.toDomain()
    }

    suspend fun getAdmin(
        userName: String,
        passwordHash: String
    ): AdminUser = dbQuery {
        val entity = AdminEntity.find(
            op = Op.build {
                (AdminUserTable.userName eq userName) and (AdminUserTable.password eq passwordHash)
            }
        ).firstOrNull() ?: throw NotFoundException("Invalid username or password!")
        return@dbQuery entity.toDomain()
    }

}