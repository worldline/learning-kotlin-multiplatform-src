import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import data.dataclasses.Quiz
import data.dataclasses.RequestTime
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import io.github.xxfast.kstore.utils.ExperimentalKStoreApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.io.files.Path
import platform.Foundation.NSDocumentDirectory

import platform.UIKit.UIDevice
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalKStoreApi::class, ExperimentalForeignApi::class)
actual fun getKStore(): KStore<RequestTime>? {
    return NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        appropriateForURL = null,
        create = false,
        inDomain = NSUserDomainMask,
        error = null
    )!!.path?.let {
        storeOf(
        file= Path(it)
    )
    }
}

actual suspend fun provideDbDriver(
    schema: SqlSchema<QueryResult.AsyncValue<Unit>>
): SqlDriver {
    return NativeSqliteDriver(schema.synchronous(), "quiz.db")
}