import com.worldline.quiz.QuizApp
import data.dataclasses.Quiz
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import okio.Path.Companion.toPath

class AndroidPlatform : Platform {
    override val name: String = "Android" //${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
actual fun getKStore(): KStore<Quiz>? {
    return storeOf(QuizApp.context().dataDir.path.plus("/quiz.json").toPath())
}
