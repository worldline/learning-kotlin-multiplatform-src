
import androidx.compose.runtime.Composable
import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import com.myapplication.common.cache.Database
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Worker

actual fun getPlatformName(): String = "JS"

@Composable
fun MainView() = PreComposeApp {
    val driver = DatabaseDriverFactory().createDriver()
    GlobalScope.launch { Database.Schema.awaitCreate(driver) }
    App(driver)
}

actual class DatabaseDriverFactory {
    actual  fun createDriver(): SqlDriver {
        return  WebWorkerDriver(
            Worker(js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")),
        )
    }
}


/*actual class DatabaseDriverFactory() {
    fun createDriver(): SqlDriver
}*/