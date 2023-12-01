import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.util.Properties


actual fun getPlatformName(): String = "Desktop"

@Composable fun MainView() = App(DatabaseDriverFactory().createDriver())

@Preview
@Composable
fun AppPreview() {
    App(DatabaseDriverFactory().createDriver())
}

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return JdbcSqliteDriver(
            url = "jdbc:sqlite:quiz.db",
            properties = Properties().apply { put("foreign_keys", "true") })
    }
}