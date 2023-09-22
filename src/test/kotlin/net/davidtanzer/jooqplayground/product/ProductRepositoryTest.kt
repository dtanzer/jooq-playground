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
import org.springframework.stereotype.Component
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@Component
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
}