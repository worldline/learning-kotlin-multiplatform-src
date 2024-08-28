import data.dataclasses.Quiz
import io.github.xxfast.kstore.KStore
import io.github.xxfast.kstore.file.storeOf
import io.github.xxfast.kstore.file.utils.DocumentDirectory
import io.github.xxfast.kstore.utils.ExperimentalKStoreApi
import okio.Path.Companion.toPath
import platform.UIKit.UIDevice
import platform.Foundation.NSFileManager

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalKStoreApi::class)
actual fun getKStore(): KStore<Quiz>? {
    return NSFileManager.defaultManager.DocumentDirectory?.relativePath?.plus("/quiz.json")?.toPath()?.let {
        storeOf(
        file= it
    )
    }
}
