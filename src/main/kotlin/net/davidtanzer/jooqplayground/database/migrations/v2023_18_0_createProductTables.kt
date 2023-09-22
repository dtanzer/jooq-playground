package net.davidtanzer.jooqplayground.database.migrations

import net.davidtanzer.jooqplayground.product.Product
import net.davidtanzer.jooqplayground.product.ProductImage
import org.jooq.DSLContext
import org.jooq.impl.DSL

fun v2023_18_0_createProductTables(dsl: DSLContext) {
	dsl.createTable(Product.table)
		.column(Product.id)
		.column(Product.name)
		.column(Product.description)
		.column(Product.price)
		.column(Product.priceCurrency)
		.constraints(DSL.primaryKey(Product.id))
		.execute()

	dsl.createTable(ProductImage.table)
		.column(ProductImage.id)
		.column(ProductImage.url)
		.column(ProductImage.description)
		.column(ProductImage.productId)
		.constraints(
			DSL.primaryKey(ProductImage.id),
			DSL.foreignKey(ProductImage.productId).references(Product.table, Product.id),
		)
		.execute()

	dsl.createIndex("products_by_name").on(Product.table, Product.name).execute()
}
