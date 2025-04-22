import data.dataclasses.Quiz
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import kotlinx.io.files.Path

class JVMPlatform : Platform {
    override val name: String = "Desktop JVM ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()
actual fun getKStore(): KStore<Quiz>? {
    return storeOf(Path("quiz.json"))
}
