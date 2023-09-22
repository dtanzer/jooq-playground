package net.davidtanzer.jooqplayground.product

import io.mockk.mockk

class ProductServiceTest {
	val deviceRepository: ProductRepository = mockk(relaxed = true)
	val deviceService = ProductService(deviceRepository)
/*
	@Test
	fun `adds the device to the list of devices if it is not already present`() {
		val expectedDevice = Product(
			productId = "devi-1234-5678",
			name = "",
			description = "",
			iconUrl = "",
			mapUrl = "",
		)
		deviceService.ensureDeviceExists(expectedDevice.productId)

		verify { deviceRepository.addIfNotPresent(expectedDevice) }
	}
 */
}