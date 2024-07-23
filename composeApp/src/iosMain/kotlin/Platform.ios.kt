import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration
import com.worldline.quiz.cache.Database
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory {
    actual  fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            DatabaseConfiguration(
                name = "quizz.db",
                version = Database.Schema.version.toInt(),
                create = { connection ->
                    wrapConnection(connection) { Database.Schema.synchronous().create(it) }
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