package net.davidtanzer.jooqplayground.order

import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType.*
import java.util.UUID

data class Address(
	val id: UUID? = null,
	val name: String,
	val line1: String,
	val line2: String? = null,
	val postalCode: String,
	val city: String,
	val country: String,
) {
	fun withId(addressId: UUID?): Address {
		return Address(addressId, this.name, this.line1, this.line2, this.postalCode, this.city, this.country)
	}

	companion object DB {
		val table = DSL.table("Address")
		val id = DSL.field("id", UUID)
		val name = DSL.field("name", VARCHAR(127))
		val line1 = DSL.field("line1", VARCHAR(255))
		val line2 = DSL.field("line2", VARCHAR(255))
		val postalCode = DSL.field("postalCode", VARCHAR(12))
		val city = DSL.field("city", VARCHAR(63))
		val country = DSL.field("country", VARCHAR(2))
	}
}
