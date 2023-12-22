

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.myapplication.common.cache.Database


actual fun getPlatformName(): String = "Android"

@Composable fun MainView(context: Context) = App(DatabaseDriverFactory(context = context).createDriver())

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = Database.Schema.synchronous(),
            context = context,
            name = "quiz.db",
            callback = object : AndroidSqliteDriver.Callback(Database.Schema.synchronous()) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            }
        )
    }
}


