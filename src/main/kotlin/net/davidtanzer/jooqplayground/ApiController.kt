package net.davidtanzer.jooqplayground

import net.davidtanzer.jooqplayground.product.ProductService
import net.davidtanzer.jooqplayground.order.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ApiController(
	//@Autowired private val deviceService: ProductService,
	//@Autowired private val sensorService: OrderService,
	//@Autowired private val aggregationService: AggregationService,
) {
	/*
	@PostMapping("/{deviceId}", consumes = ["application/json"])
	fun updateDevice(@PathVariable deviceId: String, @RequestBody deviceInput: DeviceInput) {
		deviceService.ensureDeviceExists(deviceId)

		deviceInput.sensors.forEach {
			sensorService.persistMeasurement(deviceId, it.sensorId, it.value)
		}
	}
	 */
}
