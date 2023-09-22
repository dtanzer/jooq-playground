package net.davidtanzer.jooqplayground.order

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.davidtanzer.jooqplayground.product.Product
import net.davidtanzer.jooqplayground.product.ProductRepository
import net.davidtanzer.jooqplayground.product.ProductService
import org.junit.jupiter.api.Test
import strikt.api.expectCatching
import strikt.api.expectThrows
import strikt.assertions.isA
import strikt.assertions.isFailure
import java.time.LocalDateTime
import java.util.*

class OrderServiceTest {
	private val orderRepository: OrderRepository = mockk(relaxed = true)
	private val productService: ProductService = mockk(relaxed = true)
	private val orderService = OrderService(orderRepository, productService)

	@Test
	fun `rejects order when a product is unknown`() {
		every { productService.find(any()) }.returns(null)
		val order = Order(
			null,
			listOf(OrderedProduct(quantity = 3, product = Product(UUID.fromString("a5245aa1-7e65-46a6-88f4-a2389d2c4c4e")))),
			Address(name = "Max Mustermann", line1 = "Musterstr. 6a", postalCode = "1234", city = "Musterstadt", country = "AT"),
			Address(name = "Max Mustermann", line1 = "Musterstr. 6a", postalCode = "1234", city = "Musterstadt", country = "AT"),
		)

		expectCatching { orderService.create(order) }
			.isFailure()
			.isA<ProductNotFoundException>()
	}

	@Test
	fun `creates order when a product is known`() {
		val product: Product = mockk()
		every { productService.find(any()) }.returns(product)

		val order = Order(
			null,
			listOf(OrderedProduct(quantity = 3, product = Product(UUID.fromString("a5245aa1-7e65-46a6-88f4-a2389d2c4c4e")))),
			Address(name = "Max Mustermann", line1 = "Musterstr. 6a", postalCode = "1234", city = "Musterstadt", country = "AT"),
			Address(name = "Max Mustermann", line1 = "Musterstr. 6a", postalCode = "1234", city = "Musterstadt", country = "AT"),
		)

		orderService.create(order)

		verify { orderRepository.create(order) }
	}
}
