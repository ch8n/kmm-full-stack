package dev.ch8n.sample.routes.collection

object CollectionRoutes {
    fun apiV1(path: String) = "/api/v1/$path"
    const val GET_COLLECTIONS = "collections"
    const val CREATE_COLLECTION = "collections/create"
    const val UPDATE_COLLECTION = "collections/update/{id}"
    const val UPDATE_COLLECTION_IMAGE = "collections/{id}/image"
    const val GET_COLLECTION_IMAGE = "collections/image/{version}/{id}"

    object IMAGE_COLLECTION_SERVER_PATH {
        const val RAW = "assets/images/collections/raw"
        const val P480 = "assets/images/collections/480P"
        const val P720 = "assets/images/collections/720P"
        const val P1080 = "assets/images/collections/1080P"
    }

}