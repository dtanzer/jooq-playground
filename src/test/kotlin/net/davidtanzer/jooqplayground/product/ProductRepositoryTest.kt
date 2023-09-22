package net.davidtanzer.jooqplayground.product

import net.davidtanzer.jooqplayground.RepositoryTestContextConfiguration
import net.davidtanzer.jooqplayground.TestDatabaseConfiguration
import net.davidtanzer.jooqplayground.clearDatabase
import org.jooq.impl.DefaultDSLContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
class ProductRepositoryTest {

	@TestConfiguration
	@PropertySource("persistence-repo-tests.properties")
	class ContextConfig : RepositoryTestContextConfiguration() {}

	@TestConfiguration
	class DBConfig : TestDatabaseConfiguration() {
		@Bean
		fun deviceRepository(): ProductRepository {
			return ProductRepository(dsl())
		}
	}

	@Autowired
	private lateinit var dsl: DefaultDSLContext
	@Autowired
	private lateinit var deviceRepository: ProductRepository

	@BeforeEach
	fun setup() {
		clearDatabase(dsl)
	}
/*
	@Test
	fun `stores new device in database when it does not exist yet`() {
		val device = Product("devi-1234-5678", "", "", "", "")

		deviceRepository.addIfNotPresent(device)

		val fromDb = dsl.selectCount().from(Product.table).where(Product.deviceId.eq("devi-1234-5678")).fetch()
		expectThat(fromDb[0][0]).isEqualTo(1)
	}

	@Test
	fun `does not overwrite device data in database when it exists`() {
		dsl.insertInto(
			Product.table,
			Product.deviceId, Product.name, Product.description, Product.iconUrl, Product.mapUrl
		)
			.values("devi-1234-5678", "existing", "the existing device", "device.iconUrl", "device.mapUrl")
			.execute()

		val device = Product("devi-1234-5678", "", "", "", "")

		deviceRepository.addIfNotPresent(device)

		val fromDb = dsl.select(Product.deviceId, Product.name, Product.description)
			.from(Product.table)
			.where(Product.deviceId.eq("devi-1234-5678"))
			.fetch()

		expectThat(fromDb.size).isEqualTo(1)
		expectThat(fromDb[0][0]).isEqualTo("devi-1234-5678")
		expectThat(fromDb[0][1]).isEqualTo("existing")
		expectThat(fromDb[0][2]).isEqualTo("the existing device")
	}
 */
}