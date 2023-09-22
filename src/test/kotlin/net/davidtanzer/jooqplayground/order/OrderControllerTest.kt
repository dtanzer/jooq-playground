package net.davidtanzer.jooqplayground.order

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import io.mockk.verify
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.withElementAt
import java.util.*

@WebMvcTest(OrderController::class)
class OrderControllerTest(@Autowired private val mvc: MockMvc) {
	@MockkBean(relaxed = true)
	lateinit var orderService: OrderService

	@Test
	fun `creates new order`() {
		val orderSlot = slot<Order>()
		every { orderService.create(order = capture(orderSlot)) }.answers {  }

		mvc.perform(
			post("/orders")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					"""
					{
						"billingAddress": { "name": "Max Mustermann", "line1": "Musterstr. 6a", "line2": "", "postalCode": "1234", "city": "Musterstadt", "country": "AT" },
						"shippingAddress": { "name": "Max Mustermann", "line1": "Musterstr. 6a", "line2": "", "postalCode": "1234", "city": "Musterstadt", "country": "AT" },
						"products": [
							{ "product": { "id": "a5245aa1-7e65-46a6-88f4-a2389d2c4c4e" }, "quantity": 3 },
							{ "product": { "id": "18e092c7-4e26-45de-a906-3b7abfb6740b" }, "quantity": 5 }
						]
					}
					""".trimIndent())
		)

		verify { orderService.create(any()) }
		expectThat(orderSlot.captured) {
			get { billingAddress?.name }.isEqualTo("Max Mustermann")
			get { shippingAddress?.line1 }.isEqualTo("Musterstr. 6a")
			get { products }.withElementAt(0) {
				get { product.id }.isEqualTo(UUID.fromString("a5245aa1-7e65-46a6-88f4-a2389d2c4c4e"))
				get { quantity }.isEqualTo(3)
			}
			get { products }.withElementAt(1) {
				get { product.id }.isEqualTo(UUID.fromString("18e092c7-4e26-45de-a906-3b7abfb6740b"))
				get { quantity }.isEqualTo(5)
			}
		}
	}
}