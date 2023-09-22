package net.davidtanzer.jooqplayground.product

import org.jooq.impl.DSL
import org.jooq.impl.SQLDataType.CLOB
import org.jooq.impl.SQLDataType.VARCHAR
import org.jooq.impl.SQLDataType.INTEGER
import org.jooq.impl.SQLDataType.UUID
import java.util.UUID

data class Product(
	val id: UUID?,
	val name: String? = null,
	val description: String? = null,
	val price: Price? = null,
) {
	companion object DB {
		val table = DSL.table("product")
		val id = DSL.field("productId", UUID)
		val name = DSL.field("name", VARCHAR(64))
		val description = DSL.field("description", CLOB)
		val price = DSL.field("price", INTEGER)
		val priceCurrency = DSL.field("priceCurrency", VARCHAR(3))
	}
}
