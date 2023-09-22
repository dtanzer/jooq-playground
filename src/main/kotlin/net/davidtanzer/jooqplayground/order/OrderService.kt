package net.davidtanzer.jooqplayground.order

import net.davidtanzer.jooqplayground.product.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderService(
	@Autowired private val orderRepository: OrderRepository,
	@Autowired private val productService: ProductService,
) {
	fun create(order: Order) {
		val foundProducts = order.products.map { p ->
			val product = productService.find(p.product.id!!)
			if(product == null) {
				throw ProductNotFoundException("Product ${p.product.id} not found!")
			}
			return@map p.withProduct(product)
		}

		orderRepository.create(order)
	}

	fun getAll(): List<Order> {
		return orderRepository.getAll()
	}
}
