@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE", "EXPOSED_PARAMETER_TYPE")

import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        preComposeWindow(
            title = "Quiz"
        ) {
            MainView()
        }
    }
}