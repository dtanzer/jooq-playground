package net.davidtanzer.jooqplayground.order

import net.davidtanzer.jooqplayground.database.Migrations
import net.davidtanzer.jooqplayground.product.Price
import net.davidtanzer.jooqplayground.product.Product
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.DefaultDSLContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class OrderRepository(@Autowired private val dsl: DefaultDSLContext) {
	private val logger = LoggerFactory.getLogger(Migrations::class.java)

	fun create(order: Order): Order {
		return dsl.transactionResult { config ->
			val transaction = DSL.using(config)
			val orderId = UUID.randomUUID()

			val billingAddress = insertAddress(order.billingAddress!!, transaction)
			val shippingAddress = insertAddress(order.shippingAddress!!, transaction)

			transaction
				.insertInto(Order.table, Order.id, Order.billingAddressId, Order.shippingAddressId)
				.values(orderId, billingAddress.id, shippingAddress.id)
				.execute()

			order.products.forEach {product ->
				createOrderedProduct(orderId, product, transaction)
			}

			return@transactionResult Order(
				id = orderId,
				products = order.products,
				billingAddress = billingAddress,
				shippingAddress = shippingAddress,
			)
		}
	}

	private fun createOrderedProduct(orderId: UUID?, orderedProduct: OrderedProduct, transaction: DSLContext) {
		transaction
			.insertInto(OrderedProduct.table, OrderedProduct.orderId, OrderedProduct.productId, OrderedProduct.quantity)
			.values(orderId, orderedProduct.product.id, orderedProduct.quantity)
			.execute()
	}

	private fun insertAddress(address: Address, transaction: DSLContext): Address {
		val addressId = UUID.randomUUID()

		transaction
			.insertInto(Address.table, Address.id, Address.name, Address.line1, Address.line2, Address.postalCode, Address.city, Address.country)
			.values(addressId, address.name, address.line1, address.line2, address.postalCode, address.city, address.country)
			.execute()

		return address.withId(addressId)
	}

	fun getAll(): List<Order> {
		val fromDb = dsl
			.select(Order.id, Order.billingAddressId, Order.shippingAddressId)
			.from(Order.table)
			.fetch()

		return fromDb.map {
			val orderedProductsFromDb = dsl
				.select(OrderedProduct.productId, OrderedProduct.quantity)
				.from(OrderedProduct.table)
				.where(OrderedProduct.orderId.eq(it.getValue(Order.id)))
				.fetch()

			return@map Order(
				id = it.getValue(Order.id),
				billingAddress = null,
				shippingAddress = null,
				products = orderedProductsFromDb.map {
					val productFromDb = dsl
						.select(Product.price, Product.priceCurrency, Product.name)
						.from(Product.table)
						.where(Product.id.eq(it.getValue(OrderedProduct.productId)))
						.fetch()

					return@map OrderedProduct(
						quantity = it.getValue(OrderedProduct.quantity),
						product = Product(
							id = it.getValue(OrderedProduct.productId),
							name = productFromDb.get(0).getValue(Product.name),
							price = Price(
								value =  productFromDb.get(0).getValue(Product.price),
								currency = productFromDb.get(0).getValue(Product.priceCurrency),
							)
						)
					)
				}
			)
		}
	}
}
