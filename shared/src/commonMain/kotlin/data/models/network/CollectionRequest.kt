package data.models.network

import kotlinx.serialization.Serializable

@Serializable
data class GetCollectionsRequest(
    val pageNumber: Int,
    val pageSize: Int
)

@Serializable
data class CreateCollectionRequest(
    val title: String,
    val description: String,
)

@Serializable
data class UpdateCollectionRequest(
    val collectionId: String,
    val title: String,
    val description: String,
)

