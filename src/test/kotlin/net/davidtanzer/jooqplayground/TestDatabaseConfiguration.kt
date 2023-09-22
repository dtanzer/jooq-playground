package net.davidtanzer.jooqplayground

import net.davidtanzer.jooqplayground.database.Migrations
import org.springframework.context.annotation.Bean

abstract class TestDatabaseConfiguration : DatabaseConfig() {
	@Bean
	fun migrations(): Migrations {
		return Migrations(dsl())
	}
}