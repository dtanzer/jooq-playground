package net.davidtanzer.jooqplayground.product

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.MockMvc
import strikt.api.expectThat
import strikt.assertions.isEqualTo

@WebMvcTest(ProductController::class)
class ProductControllerTest(@Autowired private val mvc: MockMvc) {
	@MockkBean(relaxed = true)
	lateinit var productService: ProductService

	@Test
	fun `creates new product`() {
		val productSlot = slot<Product>()
		every { productService.create(product = capture(productSlot)) }.answers {}

		mvc.perform(
			post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(
					"""
					{
						"productId": "a5245aa1-7e65-46a6-88f4-a2389d2c4c4e",
						"name": "The product",
						"description": "Description",
						"price": { "value": 123, "currency": "EUR" }
					}
					
				""".trimIndent())
		)

		verify { productService.create(any()) }
		expectThat(productSlot.captured) {
			get { name }.isEqualTo("The product")
			get { price?.value }.isEqualTo(123)
		}
	}
}
