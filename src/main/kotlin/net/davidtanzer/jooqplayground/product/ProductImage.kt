package net.davidtanzer.jooqplayground.product

import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType
import java.util.UUID

data class ProductImage(
	val id: UUID,
	val url: String,
	val description: String,
	val productId: UUID,
) {
	companion object DB {
		val table = DSL.table("productImage")
		val id = DSL.field("id", SQLDataType.UUID)
		val url = DSL.field("url", SQLDataType.VARCHAR(1024))
		val description = DSL.field("description", SQLDataType.CLOB)
		val productId = DSL.field("productId", SQLDataType.UUID)
	}
}
