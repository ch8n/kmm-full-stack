package dev.ch8n.sample.routes.collection

import dev.ch8n.sample.routes.collection.CollectionRoutes.CREATE_COLLECTION
import dev.ch8n.sample.routes.collection.CollectionRoutes.GET_COLLECTIONS
import dev.ch8n.sample.routes.collection.CollectionRoutes.apiV1
import dev.ch8n.sample.utils.generateHttpResponse
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.collectionsApi() {
    val collectionController by inject<CollectionController>()

    get(apiV1(GET_COLLECTIONS)) {
        val response = collectionController.getCollections(this)
        val httpResponse = generateHttpResponse(response)
        call.respond(httpResponse.code, httpResponse.body)
    }

    authenticate {
        post(apiV1(CREATE_COLLECTION)) {
            val response = collectionController.createCollection(this)
            val httpResponse = generateHttpResponse(response)
            call.respond(httpResponse.code, httpResponse.body)
        }
    }
}