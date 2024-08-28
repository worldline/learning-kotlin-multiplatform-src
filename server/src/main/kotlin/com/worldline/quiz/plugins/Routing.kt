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
        "What is the primary goal of Kotlin Multiplatform?",
        "How does Kotlin Multiplatform facilitate code sharing between platforms?",
        "Which platforms does Kotlin Multiplatform support?",
        "What is a common use case for Kotlin Multiplatform?",
        "Which naming of KMP is deprecated?",
        "How does Kotlin Multiplatform handle platform-specific implementations?",
        "At which Google I/O, Google announced first-class support for Kotlin on Android?",
        "What is the name of the Kotlin mascot?",
        "The international yearly Kotlin conference is called...",
        "Where will be located the next international yearly Kotlin conference?"
    )

    val answers = listOf(
        listOf(
            "To share code between multiple platforms",
            "To exclusively compile code to JavaScript",
            "To build only Android applications",
            "To create iOS-only applications"
        ),
        listOf(
            "By sharing business logic and adapting UI",
            "By writing separate code for each platform",
            "By using only Java libraries",
            "By using code translation tools"
        ),
        listOf(
            "Android, iOS, desktop and web",
            "Only Android",
            "Only iOS",
            "Only web applications"
        ),
        listOf(
            "Developing a cross-platform app",
            "Building a desktop-only application",
            "Creating a server-side application",
            "Writing a standalone mobile app"
        ),
        listOf(
            "Kotlin Multiplatform Mobile (KMM)",
            "Hadi Multiplatform",
            "Jetpack multiplatform",
            "Kodee multiplatform"
        ),
        listOf(
            "Through expect and actual declarations",
            "By automatically translating code",
            "By restricting to a single platform",
            "By excluding platform-specific features"
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
