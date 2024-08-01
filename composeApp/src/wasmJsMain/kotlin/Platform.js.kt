import data.dataclasses.Quiz
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.storage.storeOf

class JsPlatform: Platform {
    override val name: String = "Web with Kotlin JS"
}

actual fun getPlatform(): Platform = JsPlatform()
actual fun getKStore(): KStore<Quiz>? {
    return storeOf(key = "kstore_quiz")
 }

