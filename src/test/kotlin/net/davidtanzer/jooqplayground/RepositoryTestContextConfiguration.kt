package net.davidtanzer.jooqplayground

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

open class RepositoryTestContextConfiguration {
	@Autowired
	private lateinit var env: Environment

	@Bean
	open fun dataSource(): DataSource {
		val dataSource = DriverManagerDataSource()
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName")!!)
		dataSource.url = env.getProperty("jdbc.url")
		dataSource.username = env.getProperty("jdbc.user")
		dataSource.password = env.getProperty("jdbc.pass")
		return dataSource
	}
}