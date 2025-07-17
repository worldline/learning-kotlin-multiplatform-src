import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import com.myapplication.common.cache.Database
import data.dataclasses.RequestTime
import io.github.xxfast.kstore.KStore

object AppInitializer {
    private lateinit var database: Database
    private lateinit var kStore: KStore<RequestTime>

    suspend fun init() {
        val driver = provideDbDriver(Database.Schema)
        val db = Database(driver)
        Database.Schema.awaitCreate(driver)
        database = db
        kStore = getKStore() ?: throw IllegalStateException("KStore instance is null. Ensure getKStore() is implemented correctly.")
        //kStore.set(RequestTime(0L))
    }

    fun getDatabase(): Database? = database
    fun getKStoreInstance(): KStore<RequestTime> = kStore
}