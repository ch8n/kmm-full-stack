package dev.ch8n.sample.data.local.entities

import data.models.domain.Discount
import data.models.domain.Order
import data.models.domain.OrderItem
import data.models.domain.ProductVariant
import dev.ch8n.sample.data.local.database.tables.*
import dev.ch8n.sample.data.local.entities.DiscountEntity.Companion.toDomain
import dev.ch8n.sample.data.local.entities.OrderEntity.Companion.toDomain
import dev.ch8n.sample.data.local.entities.OrderItemEntity.Companion.toDomain
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class OrderEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    
    companion object : UUIDEntityClass<OrderEntity>(OrderTable) {

        fun OrderEntity.toDomain(): Order {
            return Order(
                id = id.value.toString(),
                customerId = customerId.value.toString(),
                orderItem = orderItems.map { orderItemEntity -> orderItemEntity.toDomain() },
                comments = Json.decodeFromString(commentsJson),
                orderStatus = orderStatus,
                discount = discount?.toDomain(),
                orderSummary = orderSummaryJson?.let { Json.decodeFromString(it) }
            )
        }
    }

    val orderItems by OrderItemEntity referrersOn OrderItemTable.orderId
    val discount by DiscountEntity.optionalReferencedOn(OrderTable.discountId)

    //val customer by CustomerEntity referencedOn OrderTable.customerId

    var customerId by OrderTable.customerId
    var orderStatus by OrderTable.orderStatus
    var commentsJson by OrderTable.commentsJson
    var discountId by OrderTable.discountId
    var orderSummaryJson by OrderTable.orderSummaryJson
}

class OrderItemEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<OrderItemEntity>(OrderItemTable) {
        fun OrderItemEntity.toDomain(): OrderItem {
            return OrderItem(
                id = id.value.toString(),
                orderId = orderId.value.toString(),
                productVariant = ProductVariant.from(productVariant),
                productQty = orderQty,
                priceInRupees = priceInRupees,
                itemComment = itemComment
            )
        }
    }

    //val order by OrderEntity referencedOn OrderItemTable.orderId

    val productVariant by ProductVariantEntity referencedOn OrderItemTable.productVariantId
    val itemComment by OrderItemTable.itemComment

    var orderId by OrderItemTable.orderId
    var productVariantId by OrderItemTable.productVariantId
    var orderQty by OrderItemTable.orderQty
    var priceInRupees by OrderItemTable.priceInRupees
}