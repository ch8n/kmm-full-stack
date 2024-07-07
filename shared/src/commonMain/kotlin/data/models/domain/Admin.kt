package data.models.domain

import kotlinx.serialization.Serializable

@Serializable
data class AdminUser(
    val adminId: String,
    val userName: String,
    val password: String
)




