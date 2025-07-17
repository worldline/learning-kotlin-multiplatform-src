import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import data.dataclasses.Quiz
import data.dataclasses.RequestTime
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf
import org.w3c.dom.Worker

class JsPlatform: Platform {
    override val name: String = "WASM"
}

actual fun getPlatform(): Platform = JsPlatform()
actual fun getKStore(): KStore<RequestTime>? {
    return storeOf(key = "kstore_quiz")
 }

actual suspend fun provideDbDriver(schema: SqlSchema<QueryResult.AsyncValue<Unit>>): SqlDriver {
    return WebWorkerDriver(
        jsWorker()
    )
}

fun jsWorker(): Worker =
    js("""new Worker(new URL("./sqljs.worker.js", import.meta.url))""")