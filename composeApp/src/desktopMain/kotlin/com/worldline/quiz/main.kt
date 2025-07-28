package com.worldline.quiz

import App
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import screens.QuestionScreen
import screens.WelcomeScreen


@Preview
@Composable
internal fun QuizScreenPreview() {
    val onFinishButtonPushed = { _: Int, _: Int -> }
    QuestionScreen(onFinishButtonPushed)
}

@Preview
@Composable
internal fun WelcomeScreenPreview() {
    val onStartButtonPushed = { }
    WelcomeScreen(onStartButtonPushed)
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "QuizApp",
        /*state = WindowState(
            size = DpSize(640.dp, 480.dp),
            position = WindowPosition(Alignment.Center)
        ),
        alwaysOnTop = true,
        undecorated = true,*/
    ) {
        App()
    }
}