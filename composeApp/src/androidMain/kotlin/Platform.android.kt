import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.worldline.quiz.QuizApp
import data.dataclasses.Quiz
import data.dataclasses.RequestTime
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path


class AndroidPlatform : Platform {
    override val name: String = "Android" //${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual fun getKStore(): KStore<RequestTime>? {
    return storeOf(Path(QuizApp.context().filesDir.path.plus("/quiz.json")))
}

actual suspend fun provideDbDriver(
    schema: SqlSchema<QueryResult.AsyncValue<Unit>>
): SqlDriver {
    return AndroidSqliteDriver(schema.synchronous(), QuizApp.context(), "quiz.db")
}