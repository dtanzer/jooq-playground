package net.davidtanzer.jooqplayground

import net.davidtanzer.jooqplayground.order.Address
import net.davidtanzer.jooqplayground.order.Order
import net.davidtanzer.jooqplayground.order.OrderedProduct
import net.davidtanzer.jooqplayground.product.Product
import net.davidtanzer.jooqplayground.product.ProductImage
import org.jooq.DSLContext

fun clearDatabase(dsl: DSLContext) {
	dsl.deleteFrom(Address.table).execute()
	dsl.deleteFrom(OrderedProduct.table).execute()
	dsl.deleteFrom(Order.table).execute()

	dsl.deleteFrom(ProductImage.table).execute()
	dsl.deleteFrom(Product.table).execute()
}
