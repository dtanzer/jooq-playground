package net.davidtanzer.jooqplayground.order

import net.davidtanzer.jooqplayground.product.Product
import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType.INTEGER
import org.jooq.impl.SQLDataType.UUID

data class OrderedProduct(
	val product: Product,
	val quantity: Int,
) {
	fun withProduct(product: Product?): OrderedProduct? {
		if(product == null) {
			return null
		}
		return OrderedProduct(
			product,
			this.quantity
		)
	}

	companion object DB {
		val table = DSL.table("OrderedProduct")

		val orderId = DSL.field("orderId", UUID)
		val productId = DSL.field("productId", UUID)
		val quantity = DSL.field("quantity", INTEGER)
	}
}
