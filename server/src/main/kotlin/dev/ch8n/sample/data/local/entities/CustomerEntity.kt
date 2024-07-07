package dev.ch8n.sample.data.local.entities

import data.models.domain.Customer
import data.models.domain.CustomerAddress
import dev.ch8n.sample.data.local.database.tables.*
import dev.ch8n.sample.data.local.entities.CustomerAddressEntity.Companion.toDomain
import dev.ch8n.sample.data.local.entities.CustomerEntity.Companion.toDomain
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CustomerEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<CustomerEntity>(CustomerTable) {
        fun CustomerEntity.toDomain(): Customer = Customer(
            id = id.value.toString(),
            fullName = fullName,
            phone = phone,
            email = email,
            address = address.map { it.toDomain() }
        )
    }

    var fullName by CustomerTable.fullName
    var phone by CustomerTable.phone
    var email by CustomerTable.email

    val address by CustomerAddressEntity referrersOn CustomerAddressTable.customerId
}

class CustomerAddressEntity(id: EntityID<UUID>) : UUIDEntity(id) {

    companion object : UUIDEntityClass<CustomerAddressEntity>(CustomerAddressTable) {
        fun CustomerAddressEntity.toDomain(): CustomerAddress = CustomerAddress(
            id = id.value.toString(),
            customerId = customerId.value.toString(),
            address = address,
            city = city,
            state = state,
            pinCode = pinCode,
            contactPhone = contactPhone
        )
    }

    var customerId by CustomerAddressTable.customerId
    var address by CustomerAddressTable.address
    var city by CustomerAddressTable.city
    var state by CustomerAddressTable.state
    var pinCode by CustomerAddressTable.pinCode
    var contactPhone by CustomerAddressTable.contactPhone
}
