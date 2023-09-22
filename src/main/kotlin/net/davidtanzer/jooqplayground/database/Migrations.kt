package net.davidtanzer.jooqplayground.database


import jakarta.annotation.PostConstruct
import net.davidtanzer.jooqplayground.database.migrations.v2023_18_1_createOrderTables
import net.davidtanzer.jooqplayground.database.migrations.v2023_18_0_createProductTables
import org.jooq.DSLContext
import org.jooq.impl.DSL.*
import org.jooq.impl.DefaultDSLContext
import org.jooq.impl.SQLDataType.TIMESTAMP
import org.jooq.impl.SQLDataType.VARCHAR
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDateTime

@Component
class Migrations(
	private val dsl: DefaultDSLContext
) {
	private val migrationsTable = object {
		val table = table("migrations")
		val id = field("migration_id", VARCHAR(20).nullable(false))
		val migrated = field("migrated_at", TIMESTAMP)
	}
	private val logger = LoggerFactory.getLogger(Migrations::class.java)

	@PostConstruct
	fun runMigrations() {
		createMigrationsTable()
		runMigration("v2023.18.0", ::v2023_18_0_createProductTables)
		runMigration("v2023.18.1", ::v2023_18_1_createOrderTables)
	}

	private fun createMigrationsTable() {
		dsl.transaction { config ->
			val context = using(config)

			logger.info("DB migrations: Creating table (if not exists)")
			context.createTableIfNotExists(migrationsTable.table)
				.column(migrationsTable.id)
				.column(migrationsTable.migrated)
				.constraints(primaryKey(migrationsTable.id))
				.execute()

		}
	}

	private fun runMigration(id: String, migration: (d: DSLContext) -> Unit) {
		dsl.transaction { config ->
			val context = using(config)

			val alreadyMigratedEntry = context.select(migrationsTable.id)
				.from(migrationsTable.table)
				.where(migrationsTable.id.eq(id))
				.fetch()
			logger.info("For migration $id: Found in Database ${alreadyMigratedEntry.size}")

			if (alreadyMigratedEntry.size == 0) {
				logger.info("DB migrations: RUNNING migration $id")
				migration(context)

				context.insertInto(
					migrationsTable.table,
					migrationsTable.id, migrationsTable.migrated
				)
					.values(id, Timestamp.valueOf(LocalDateTime.now()))
					.execute()
			} else {
				logger.info("DB migrations: Migration $id already applied, nothing to be done")
			}
		}
	}
}
