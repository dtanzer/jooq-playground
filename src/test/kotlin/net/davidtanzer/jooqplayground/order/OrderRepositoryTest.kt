package net.davidtanzer.jooqplayground.order

import net.davidtanzer.jooqplayground.RepositoryTestContextConfiguration
import net.davidtanzer.jooqplayground.TestDatabaseConfiguration
import net.davidtanzer.jooqplayground.clearDatabase
import net.davidtanzer.jooqplayground.product.Product
import org.jooq.impl.DefaultDSLContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import org.springframework.test.context.junit.jupiter.SpringExtension
import strikt.api.expectThat
import strikt.assertions.*
import java.time.LocalDateTime
import java.util.*

@ExtendWith(SpringExtension::class)
@Component
class OrderRepositoryTest {
	@TestConfiguration
	@PropertySource("persistence-repo-tests.properties")
	class ContextConfig : RepositoryTestContextConfiguration() {}

	@TestConfiguration
	class DBConfig : TestDatabaseConfiguration() {
		@Bean
		fun sensorRepository(): OrderRepository {
			return OrderRepository(dsl())
		}
	}

	@Autowired
	private lateinit var dsl: DefaultDSLContext
	@Autowired
	private lateinit var orderRepository: OrderRepository

	@BeforeEach
	fun setup() {
		clearDatabase(dsl)
		dsl
			.insertInto(Product.table, Product.id)
			.values(UUID.fromString("a5245aa1-7e65-46a6-88f4-a2389d2c4c4e"))
			.execute()
	}

	@Test
	fun `creates order table entry on "create"`() {
		val order = createOrder()

		val created = orderRepository.create(order)

		val ordersFromDb = dsl
			.select(Order.id).from(Order.table)
			.fetch()

		expectThat(created.id).isNotNull()

		expectThat(ordersFromDb) {
			hasSize(1)
			withElementAt(0) {
				get { getValue(Order.id) }.isA<UUID>().isEqualTo(created.id)
			}
		}
	}

	@Test
	fun `creates address entries on "create"`() {
		val order = createOrder()
		val created = orderRepository.create(order)

		expectThat(created) {
			get { billingAddress?.id }.isNotNull()
			get { shippingAddress?.id }.isNotNull()
		}
		val orderFromDb = dsl
			.select(Order.id, Order.billingAddressId, Order.shippingAddressId)
			.from(Order.table)
			.fetch()
		expectThat(orderFromDb) {
			hasSize(1)
			withElementAt(0) {
				get { getValue(Order.billingAddressId) }.isA<UUID>().isContainedIn(listOf(created.billingAddress?.id!!, created.shippingAddress?.id!!))
				get { getValue(Order.shippingAddressId) }.isA<UUID>().isContainedIn(listOf(created.billingAddress?.id!!, created.shippingAddress?.id!!))
			}
		}
		val addressesFromDb = dsl
			.select(Address.id, Address.name)
			.from(Address.table)
			.fetch()
		expectThat(addressesFromDb) {
			hasSize(2)
			withElementAt(0) {
				get { getValue(Address.id) }.isA<UUID>().isContainedIn(listOf(created.billingAddress?.id!!, created.shippingAddress?.id!!))
				get { getValue(Address.name) }.isEqualTo("Max Mustermann")
			}
			withElementAt(1) {
				get { getValue(Address.id) }.isA<UUID>().isContainedIn(listOf(created.billingAddress?.id!!, created.shippingAddress?.id!!))
				get { getValue(Address.name) }.isEqualTo("Max Mustermann")
			}
		}
	}

	@Test
	fun `creates ordered products on "create"`() {
		val order = createOrder()
		val created = orderRepository.create(order)

		val productsFromDB = dsl
			.select(OrderedProduct.orderId, OrderedProduct.productId, OrderedProduct.quantity)
			.from(OrderedProduct.table)
			.fetch()

		expectThat(productsFromDB) {
			hasSize(1)
			withElementAt(0) {
				get { getValue(OrderedProduct.orderId) }.isA<UUID>().isEqualTo(created.id)
				get { getValue(OrderedProduct.productId) }.isA<UUID>().isEqualTo(order.products[0].product.id)
				get { getValue(OrderedProduct.quantity) }.isEqualTo(created.products[0].quantity)
			}
		}
	}

	private fun createOrder(): Order {
		return Order(
			null,
			listOf(OrderedProduct(quantity = 3, product = Product(UUID.fromString("a5245aa1-7e65-46a6-88f4-a2389d2c4c4e")))),
			Address(name = "Max Mustermann", line1 = "Musterstr. 6a", postalCode = "1234", city = "Musterstadt", country = "AT"),
			Address(name = "Max Mustermann", line1 = "Musterstr. 6a", postalCode = "1234", city = "Musterstadt", country = "AT"),
		)
	}
}