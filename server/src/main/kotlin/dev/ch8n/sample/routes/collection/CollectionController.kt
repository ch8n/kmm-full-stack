package dev.ch8n.sample.routes.collection

import data.models.domain.Collection
import data.models.network.CreateCollectionRequest
import data.models.network.GetCollectionsRequest
import data.models.network.Response
import data.models.network.UpdateCollectionRequest
import dev.ch8n.sample.data.local.dao.CollectionsDAO
import dev.ch8n.sample.data.services.ImageCompressionService
import dev.ch8n.sample.data.services.ImageResolution
import dev.ch8n.sample.utils.ErrorMessages
import utils.ResultOf
import dev.ch8n.sample.utils.paramOrBadRequest
import dev.ch8n.sample.utils.receiveOrBadRequest
import io.ktor.client.engine.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import java.io.File

class CollectionController(
    private val collectionsDAO: CollectionsDAO,
    private val imageCompressionService: ImageCompressionService
) {
    suspend fun getCollections(routingContext: RoutingContext): Response<List<Collection>?> {
        return try {
            val getCollectionRequest = routingContext.receiveOrBadRequest<GetCollectionsRequest>()
            val collections = collectionsDAO.getCollections(
                pageNumber = getCollectionRequest.pageNumber,
                pageSize = getCollectionRequest.pageSize
            )
            Response.success(collections)
        } catch (e: Exception) {
            Response.error(
                message = buildString {
                    append("failed to get collections : ")
                    append(e.message)
                    appendLine("cause: ${e.cause?.message ?: ErrorMessages.SOMETHING_WENT_WRONG.message}")
                }
            )
        }
    }

    suspend fun updateCollection(
        routingContext: RoutingContext
    ): Response<Collection?> {
        return try {
            val updateCollectionRequest = routingContext
                .receiveOrBadRequest<UpdateCollectionRequest>()
            val collection = collectionsDAO.updateCollection(
                collectionId = updateCollectionRequest.collectionId,
                title = updateCollectionRequest.title,
                description = updateCollectionRequest.description,
            )
            Response.success(collection)
        } catch (e: Exception) {
            Response.error(
                message = buildString {
                    append("failed to create collections : ")
                    append(e.message)
                    appendLine("cause: ${e.cause?.message ?: ErrorMessages.SOMETHING_WENT_WRONG.message}")
                }
            )
        }
    }

    suspend fun createCollection(
        routingContext: RoutingContext
    ): Response<Collection?> {
        return try {
            val createCollectionRequest = routingContext.receiveOrBadRequest<CreateCollectionRequest>()
            val collection = collectionsDAO.createCollection(
                title = createCollectionRequest.title,
                description = createCollectionRequest.description,
            )
            Response.success(collection)
        } catch (e: Exception) {
            Response.error(
                message = buildString {
                    append("failed to create collections : ")
                    append(e.message)
                    appendLine("cause: ${e.cause?.message ?: ErrorMessages.SOMETHING_WENT_WRONG.message}")
                }
            )
        }
    }

    suspend fun updateCollectionImage(
        routingContext: RoutingContext
    ): Response<Collection?> {
        return try {

            val collectionId = routingContext.paramOrBadRequest("id")
            val multipartFile = routingContext.call.receiveMultipart()
            val collection = collectionsDAO.getCollection(collectionId)
            val imageId = buildString {
                append(collectionId)
                append("_")
                append(collection.title.replace(" ", "_"))
                append(".jpg")
            }

            val collectionImagesRawPath = CollectionRoutes.IMAGE_COLLECTION_SERVER_PATH.RAW
            val collectionImagesRawDir = File(collectionImagesRawPath)

            if (collectionImagesRawDir.exists().not()) collectionImagesRawDir.mkdirs()

            val collectionImageFile = File("$collectionImagesRawPath/$imageId")

            multipartFile.forEachPart { part ->
                if (part is PartData.FileItem) {
                    val fileBytes = part.streamProvider().readBytes()
                    collectionImageFile.writeBytes(fileBytes)
                }
                part.dispose()
            }

            ImageResolution.entries.onEach { resolution ->
                imageCompressionService.compressAndSaveImage(inputFile = collectionImageFile, resolution = resolution)
            }

            val updatedCollection =
                collectionsDAO.updateCollection(collectionId = collectionId, imageIdentifier = imageId)
            Response.success(updatedCollection)
        } catch (e: Exception) {
            Response.error(
                message = buildString {
                    append("failed to upload collections image : ")
                    append(e.message)
                    appendLine("cause: ${e.cause?.message ?: ErrorMessages.SOMETHING_WENT_WRONG.message}")
                }
            )
        }
    }

    suspend fun downloadCollectionImage(
        routingContext: RoutingContext
    ): ResultOf<File> {
        return ResultOf.build {
            val version = routingContext.paramOrBadRequest("version")
            val imageId = routingContext.paramOrBadRequest("id")
            val resolution = ImageResolution.valueOf(version)
            val collectionPath = when (resolution) {
                ImageResolution.P480 -> CollectionRoutes.IMAGE_COLLECTION_SERVER_PATH.P480
                ImageResolution.P720 -> CollectionRoutes.IMAGE_COLLECTION_SERVER_PATH.P720
                ImageResolution.P1080 -> CollectionRoutes.IMAGE_COLLECTION_SERVER_PATH.P1080
            }

            val file = File(collectionPath, imageId)
            if (file.exists()) {
                file
            } else {
                throw NotFoundException("Image id $imageId of resolution $resolution not found!")
            }
        }
    }

}
