package net.davidtanzer.jooqplayground.order

import net.davidtanzer.jooqplayground.database.Migrations
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(
	@Autowired private val orderService: OrderService,
) {
	private val logger = LoggerFactory.getLogger(OrderController::class.java)

	@PostMapping(consumes = ["application/json"])
	fun create(@RequestBody order: Order) {
		logger.info("order service "+orderService)
		orderService.create(order)
	}

	@GetMapping(produces = ["application/json"], consumes = ["application/json"])
	fun getAll(): List<Order> {
		return orderService.getAll()
	}
}
