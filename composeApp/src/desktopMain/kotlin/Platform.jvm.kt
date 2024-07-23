import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.worldline.quiz.cache.Database
import io.ktor.http.*
import java.util.*

class JVMPlatform: Platform {
    override val name: String = "Desktop"//"Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory {
    actual  fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        driver.also {
            Database.Schema.synchronous().create(it)
            Properties().apply { put("foreign_keys", "true") }
            Url("jdbc:sqlite:quiz.db")

        }
        return driver
    }
}
