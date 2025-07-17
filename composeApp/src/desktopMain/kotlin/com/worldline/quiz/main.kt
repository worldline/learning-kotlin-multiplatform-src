package com.worldline.quiz

import App
import androidx.compose.desktop.ui.tooling.preview.Preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import screens.QuestionScreen
import screens.WelcomeScreen


@Preview
@Composable
internal fun QuizScreenPreview() {
    val onFinishButtonPushed = { _: Int, _: Int -> }
    QuestionScreen( onFinishButtonPushed)
}

@Preview
@Composable
internal fun WelcomeScreenPreview() {
    val onStartButtonPushed = { }
    WelcomeScreen(onStartButtonPushed)
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "QuizApp") {
        App()
    }
}