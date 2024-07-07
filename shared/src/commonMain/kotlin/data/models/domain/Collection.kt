package data.models.domain

import kotlinx.serialization.Serializable

@Serializable
data class Collection(
    val id: String,
    val title: String,
    val description: String,
    val imageIdentifier: String
)
