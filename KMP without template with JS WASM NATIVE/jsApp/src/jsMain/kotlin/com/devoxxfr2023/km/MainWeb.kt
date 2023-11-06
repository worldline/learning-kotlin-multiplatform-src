@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE", "EXPOSED_PARAMETER_TYPE")
package tech.worldline.demo


import androidx.compose.ui.native.ComposeLayer
import com.devoxxfr2023.km.JsApp
import kotlinx.browser.document
import kotlinx.browser.window
import moe.tlaster.precompose.preComposeWindowOverride
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.HTMLCanvasElement



// All of this is to manually handle window resizes for ComposeJS
// until it's implemented in the library.

var canvas = document.getElementById("ComposeTarget") as HTMLCanvasElement

fun canvasResize(width: Int = window.innerWidth, height: Int = window.innerHeight) {
    canvas.setAttribute("width", "$width")
    canvas.setAttribute("height", "$height")
}

fun composableResize(layer: ComposeLayer) {
    val clone = canvas.cloneNode(false) as HTMLCanvasElement
    canvas.replaceWith(clone)
    canvas = clone

    val scale = layer.layer.contentScale
    layer.layer.attachTo(clone)
    layer.layer.needRedraw()
    layer.setSize(
        (clone.width / scale).toInt(),
        (clone.height / scale).toInt()
    )
}

fun main() {
    onWasmReady {
        canvasResize()
        preComposeWindowOverride (
            title = "Sample"
        ) {
            // Listen for window resizes and update the canvas.
            window.addEventListener("resize", {
                composableResize(layer = layer)
            })

            JsApp()
        }
    }
}