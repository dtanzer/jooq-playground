package net.davidtanzer.jooqplayground.order

import net.davidtanzer.jooqplayground.product.Product
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType.*
import java.time.LocalDateTime
import java.util.UUID

data class Order(
	val id: UUID?,
	val products: List<OrderedProduct>,
	val billingAddress: Address?,
	val shippingAddress: Address?,
) {
	val total: Int
		get() { return products.sumOf { p -> (p.product.price?.value ?: 0) * (p.quantity ?: 0) } }

	companion object DB {
		val table = DSL.table("OrderTable")

		val id = DSL.field("id", UUID)
		val billingAddressId = DSL.field("billingAddressId", UUID)
		val shippingAddressId = DSL.field("shippingAddressId", UUID)
	}
}
