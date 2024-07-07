package dev.ch8n.sample.data.local.entities

import data.models.domain.Discount
import data.models.domain.DiscountCalculation
import data.models.domain.MinimumPurchaseRequirement
import dev.ch8n.sample.data.local.database.tables.*
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class DiscountEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<DiscountEntity>(DiscountTable) {
        fun DiscountEntity.toDomain(): Discount {
            return Discount(
                id = id.value.toString(),
                discountName = discountName,
                discountType = discountType,
                discountCalculation = DiscountCalculation(
                    orderDiscountCalculationType = orderDiscountCalculationType,
                    discountCalculationValue = orderDiscountCalculationValue,
                    discountCalculationUpto = orderDiscountCalculationUpto,
                    freeProductId = freeProductId
                ),
                minimumPurchaseRequirement = MinimumPurchaseRequirement(
                    minPurchaseRequirementType = minimumPurchaseRequirementType,
                    minPurchaseValue = minimumPurchaseRequirementValue
                ),
                discountStatus = discountStatus,
                timesUsed = timesUsed
            )
        }
    }

    var discountName by DiscountTable.discountName
    var discountType by DiscountTable.discountType

    var orderDiscountCalculationType by DiscountTable.orderDiscountCalculationType
    var orderDiscountCalculationValue by DiscountTable.orderDiscountCalculationValue
    var orderDiscountCalculationUpto by DiscountTable.orderDiscountCalculationUpto
    var freeProductId by DiscountTable.freeProductsId

    var minimumPurchaseRequirementType by DiscountTable.minimumPurchaseRequirementType
    var minimumPurchaseRequirementValue by DiscountTable.minimumPurchaseRequirementValue

    var discountStatus by DiscountTable.discountStatus
    var timesUsed by DiscountTable.timesUsed
}