package dev.ch8n.sample.routes

import dev.ch8n.sample.routes.auth.authApis
import dev.ch8n.sample.routes.collection.collectionsApi
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureMainRouting() {
    routing {
        get("/") {
            call.respondText(
                """
                # Welcome to narighena.com apis!
                
                - ImageVersions
                    - P480
                    - P720
                    - P1080

                - Collections Api
                    -  GET_COLLECTIONS -> /api/v1/collections
                    -  CREATE_COLLECTION -> /api/v1/collections/create
                    -  UPDATE_COLLECTION_IMAGE -> /api/v1/collections/{id}/image
                    -  GET_COLLECTION_IMAGE -> /api/v1/collections/image/{version}/{id}
                        
            """.trimIndent()
            )
        }
        authApis()
        collectionsApi()
    }
}
