package com.worldline.quiz.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import network.data.Answer
import network.data.Question
import network.data.Quiz
import kotlin.random.Random


fun Application.configureRouting() {

    routing {
        get("/quiz") {
            call.respond(generateQuiz())
        }
        staticResources("/", "static")
    }
}

fun generateQuiz(): Quiz {
    val quizQuestions = mutableListOf<Question>()

    val questions = listOf(
        "Which platforms does Kotlin Multiplatform support?",
        "Which naming of KMP is deprecated?",
        "At which Google I/O, Google announced first-class support for Kotlin on Android?",
        "What is the name of the Kotlin mascot?",
        "The international yearly Kotlin conference is called...",
        "Where will be located the next international yearly Kotlin conference?"
    )

    val answers = listOf(
        listOf(
            "Android, iOS, desktop and web",
            "Only Android",
            "Only iOS",
            "Only web applications"
        ),
        listOf(
            "Kotlin Multiplatform Mobile (KMM)",
            "Hadi Multiplatform",
            "Jetpack multiplatform",
            "Kodee multiplatform"
        ),
        listOf(
            "2017",
            "2016",
            "2014",
            "2020"
        ),
        listOf(
            "Kodee",
            "Hadee",
            "Kotlinee",
            "Kotee"
        ),
        listOf(
            "KotlinConf",
            "KodeeConf",
            "KConf",
            "KotlinKonf"
        ),
        listOf(
            "Copenhagen, Denmark",
            "Amsterdam, Netherlands",
            "Tokyo, Japan",
            "Lille, France"
        )
    )

    for (i in questions.indices) {
        val shuffledAnswers = answers[i].shuffled(Random.Default)
        val correctAnswerId = shuffledAnswers.indexOfFirst { it == answers[i][0] } + 1
        val question =
            Question(i + 1L, questions[i], correctAnswerId.toLong(), shuffledAnswers.mapIndexed { index, answer ->
                Answer(index + 1L, answer)
            })
        quizQuestions.add(question)
    }

    return Quiz(quizQuestions)
}
