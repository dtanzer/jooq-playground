package net.davidtanzer.jooqplayground.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
	@Autowired private val productService: ProductService
) {
	@PostMapping(consumes = ["application/json"])
	fun create(@RequestBody product: Product) {
		productService.create(product)
	}

	@GetMapping(produces = ["application/json"], consumes = ["application/json"])
	fun getAll(): List<Product> {
		return productService.getAll()
	}
}
