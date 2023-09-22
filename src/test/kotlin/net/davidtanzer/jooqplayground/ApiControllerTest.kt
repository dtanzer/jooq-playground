package net.davidtanzer.jooqplayground

import com.ninjasquad.springmockk.MockkBean
import io.mockk.verify
import net.davidtanzer.jooqplayground.product.ProductService
import net.davidtanzer.jooqplayground.order.OrderService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

@WebMvcTest(ApiController::class)
class ApiControllerTest(@Autowired private val mvc: MockMvc) {
	/*
	@MockkBean(relaxed = true)
	lateinit var deviceService: ProductService

	@MockkBean(relaxed = true)
	lateinit var sensorService: OrderService

	@MockkBean(relaxed = true)
	lateinit var aggregationService: AggregationService

	@Test
	fun `uses device service to make sure the device is in the database on update`() {
		val deviceId = "devi-1234-5678"

		mvc.perform(
			post("/api/$deviceId")
				.contentType("application/json")
				.content("""{ "sensors": [] }""")
		)

		verify { deviceService.ensureDeviceExists(deviceId) }
	}

	@Test
	fun `uses sensor service to persist sensor data on update`() {
		val deviceId = "devi-1234-5678"
		val sensorId = "sens-9876-5432"
		val value = 1.235

		mvc.perform(
			post("/api/$deviceId")
				.contentType("application/json")
				.content("""{ "sensors": [ { "sensorId": "$sensorId", "value": $value }] }""")
		)

		verify { sensorService.persistMeasurement(deviceId, sensorId, value) }
	}
	*/
}
