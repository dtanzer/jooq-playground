package net.davidtanzer.jooqplayground


import org.jooq.SQLDialect
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import javax.sql.DataSource


@Configuration
class DatabaseConfig {
	@Autowired
	private val dataSource: DataSource? = null

	@Bean
	fun connectionProvider(): DataSourceConnectionProvider? {
		return DataSourceConnectionProvider(TransactionAwareDataSourceProxy(dataSource!!))
	}

	@Bean
	fun dsl(): DefaultDSLContext {
		return DefaultDSLContext(configuration())
	}

	fun configuration(): DefaultConfiguration? {
		val jooqConfiguration = DefaultConfiguration()
		jooqConfiguration.set(connectionProvider())
		jooqConfiguration.set(SQLDialect.H2) //FIXME should be dynamic! defined by properties!
		//jooqConfiguration.set(DefaultExecuteListenerProvider(exceptionTransformer()))
		return jooqConfiguration
	}
}
