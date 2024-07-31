


class JsPlatform: Platform {
    override val name: String = "Web with Kotlin JS"
}

actual fun getPlatform(): Platform = JsPlatform()

/*@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory {
    actual  fun createDriver(): SqlDriver {
        return  WebWorkerDriver(
            Worker(scriptURL = js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")),
        )
    }
}*/
