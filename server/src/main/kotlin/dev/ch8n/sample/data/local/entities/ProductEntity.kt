package dev.ch8n.sample.data.local.entities

import data.models.domain.Product
import data.models.domain.ProductVariant
import dev.ch8n.sample.data.local.database.tables.ProductVariantsTable
import dev.ch8n.sample.data.local.database.tables.ProductsTable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class ProductEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<ProductEntity>(ProductsTable) {
        fun ProductEntity.toDomain(): Product = Product(
            id = id.value.toString(),
            collectionId = collectionId.value.toString(),
        )
    }

    var collectionId by ProductsTable.collectionId
}

class ProductVariantEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<ProductVariantEntity>(ProductVariantsTable) {
        fun ProductVariantEntity.toDomain(): ProductVariant = ProductVariant(
            id = id.value.toString(),
            title = title,
            description = description,
            productId = productId.value.toString(),
            imageUrls = Json.decodeFromString(imageUrlsJson),
            priceInRupees = priceInRupees,
            comparedPriceInRupees = comparedPriceInRupees,
            weightInKg = weightInKg,
            material = material,
            productStatus = productStatus,
            productQty = stockQty
        )
    }

    var title by ProductVariantsTable.title
    var description by ProductVariantsTable.description
    var productId by ProductVariantsTable.productId
    var imageUrlsJson by ProductVariantsTable.imageUrlsJson
    var priceInRupees by ProductVariantsTable.priceInRupees
    var comparedPriceInRupees by ProductVariantsTable.comparedPriceInRupees
    var weightInKg by ProductVariantsTable.weightInGrams
    var material by ProductVariantsTable.material
    var productStatus by ProductVariantsTable.productStatus
    var stockQty by ProductVariantsTable.stockQty
}
