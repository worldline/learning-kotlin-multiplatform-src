package com.devoxxfr2023.km

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

class WebPlatform : Platform {
    override val name: String = "js"
}

actual fun getPlatform(): Platform = WebPlatform()


@Composable
internal actual fun getMyImage( imageName:String): Painter {
    TODO("Not yet implemented")
}