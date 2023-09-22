package net.davidtanzer.jooqplayground.product

import net.davidtanzer.jooqplayground.database.Migrations
import org.jooq.DSLContext
import org.jooq.exception.IntegrityConstraintViolationException
import org.jooq.impl.DSL
import org.jooq.impl.DSL.using
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ProductRepository(@Autowired private val dsl: DSLContext) {
	fun create(product: Product) {
		dsl.transaction { config ->
			val transaction = DSL.using(config)
			val productId = UUID.randomUUID()

			transaction
				.insertInto(Product.table, Product.id, Product.name, Product.description, Product.price, Product.priceCurrency)
				.values(productId, product.name, product.description, product.price?.value, product.price?.currency)
				.execute()
		}
	}

	fun getAll(): List<Product> {
		val fromDb =  dsl
			.select(Product.id, Product.name, Product.description, Product.price, Product.priceCurrency)
			.from(Product.table)
			.fetch()

		return fromDb.map { productFromDb ->
			Product(
				id = productFromDb.getValue(Product.id),
				name = productFromDb.getValue(Product.name),
				description = productFromDb.getValue(Product.description),
				price = Price(
					value = productFromDb.getValue(Product.price),
					currency = productFromDb.getValue(Product.priceCurrency)
				)
			)
		}
	}

	fun find(id: UUID): Product? {
		val fromDb =  dsl
			.select(Product.id, Product.name, Product.description, Product.price, Product.priceCurrency)
			.from(Product.table)
			.where(Product.id.eq(id))
			.fetch()

		if(fromDb.size == 1) {
			return Product(
				id = fromDb.get(0).getValue(Product.id),
				name =  fromDb.get(0).getValue(Product.name),
				description =  fromDb.get(0).getValue(Product.description),
				price = Price(
					value =  fromDb.get(0).getValue(Product.price),
					currency =  fromDb.get(0).getValue(Product.priceCurrency)
				)
			)
		}
		return null
	}
}
