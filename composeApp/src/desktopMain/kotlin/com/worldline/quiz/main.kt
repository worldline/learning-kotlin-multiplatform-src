package com.worldline.quiz

import App
import androidx.compose.desktop.ui.tooling.preview.Preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import screens.questionScreen
import screens.welcomeScreen


@Preview
@Composable
internal fun quizScreenPreview() {
    val onFinishButtonPushed = { _: Int, _: Int -> }
    questionScreen( onFinishButtonPushed)
}

@Preview
@Composable
internal fun welcomeScreenPreview() {
    val onStartButtonPushed = { }
    welcomeScreen(onStartButtonPushed)
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "QuizApp") {
        App()
    }
}