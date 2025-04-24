import data.dataclasses.Quiz
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
actual fun getKStore(): KStore<Quiz>? {
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
