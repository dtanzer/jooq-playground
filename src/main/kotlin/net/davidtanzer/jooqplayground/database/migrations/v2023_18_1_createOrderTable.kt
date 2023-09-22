package net.davidtanzer.jooqplayground.database.migrations

import net.davidtanzer.jooqplayground.order.Address
import net.davidtanzer.jooqplayground.order.Order
import net.davidtanzer.jooqplayground.order.OrderedProduct
import net.davidtanzer.jooqplayground.product.Product
import org.jooq.DSLContext
import org.jooq.impl.DSL

fun v2023_18_1_createOrderTables(dsl: DSLContext) {
	dsl.createTable(Order.table)
		.column(Order.id)
		.column(Order.billingAddressId)
		.column(Order.shippingAddressId)
		.constraints(DSL.primaryKey(Order.id))
		.execute()

	dsl.createTable(Address.table)
		.column(Address.id)
		.column(Address.name)
		.column(Address.line1)
		.column(Address.line2)
		.column(Address.postalCode)
		.column(Address.city)
		.column(Address.country)
		.constraints(DSL.primaryKey(Address.id))
		.execute()

	dsl.createTable(OrderedProduct.table)
		.column(OrderedProduct.productId)
		.column(OrderedProduct.orderId)
		.column(OrderedProduct.quantity)
		.constraints(
			DSL.primaryKey(OrderedProduct.orderId, OrderedProduct.productId),
			DSL.foreignKey(OrderedProduct.productId).references(Product.table, Product.id),
			DSL.foreignKey((OrderedProduct.orderId)).references(Order.table, Order.id),
		)
		.execute()
}
