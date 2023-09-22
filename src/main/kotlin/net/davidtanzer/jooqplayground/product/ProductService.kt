package net.davidtanzer.jooqplayground.product

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(@Autowired private val productRepository: ProductRepository) {
	fun find(id: UUID): Product? {
		return productRepository.find(id)
	}

	fun create(product: Product) {
		productRepository.create(product)
	}

	fun getAll(): List<Product> {
		return productRepository.getAll()
	}

}