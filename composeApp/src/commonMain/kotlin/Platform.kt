import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import data.dataclasses.Quiz
import data.dataclasses.RequestTime
import io.github.xxfast.kstore.KStore

interface Platform {
    val name: String
}
expect fun getPlatform(): Platform
expect fun getKStore(): KStore<RequestTime>?

expect suspend fun provideDbDriver(
    schema: SqlSchema<QueryResult.AsyncValue<Unit>>
): SqlDriver