import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration

import com.myapplication.common.cache.Database

import moe.tlaster.precompose.PreComposeApplication

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = PreComposeApplication() { App(DatabaseDriverFactory().createDriver()) }


actual class DatabaseDriverFactory {
    @Suppress("MISSING_DEPENDENCY_CLASS")
    actual  fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            DatabaseConfiguration(
                name = "quizz.db",
                version = Database.Schema.version.toInt(),
                create = { connection ->
                    wrapConnection(connection) { Database.Schema.create(it) }
                },
                upgrade = { connection, oldVersion, newVersion ->
                    wrapConnection(connection) { Database.Schema.migrate(it,
                        oldVersion.toLong(), newVersion.toLong()
                    ) }
                },
                extendedConfig = DatabaseConfiguration.Extended(
                    foreignKeyConstraints = true
                )
            ),
            maxReaderConnections = 1
        )

    }
}


