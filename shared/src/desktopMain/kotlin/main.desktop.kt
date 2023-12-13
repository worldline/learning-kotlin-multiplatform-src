
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.myapplication.common.cache.Database
import io.ktor.http.Url
import java.util.Properties


actual fun getPlatformName(): String = "Desktop"

@Composable fun MainView() = App(DatabaseDriverFactory().createDriver())

@Preview
@Composable
fun AppPreview() {
    App(DatabaseDriverFactory().createDriver())
}

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