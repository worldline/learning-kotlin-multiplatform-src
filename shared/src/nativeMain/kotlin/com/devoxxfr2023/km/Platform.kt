package com.devoxxfr2023.km

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter


class NativePlatform: Platform {
    override val name: String = "Native"
}
actual fun getPlatform(): Platform = NativePlatform()


@Composable
internal actual fun getMyImage( imageName:String): Painter {
    TODO("Not yet implemented")
}