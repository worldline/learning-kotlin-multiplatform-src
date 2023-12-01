package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Quiz(var questions: List<Question>)

@Serializable
data class Answer(val id: Long, val label: String )

@Serializable
data class Question(val id:Long, val label:String, @SerialName("correct_answer_id") val correctAnswerId:Int, val answers:List<Answer>)

fun Application.configureRouting() {

    routing {
        get("/quiz") {
            call.respond(generateQuiz())
        }
    }
}

fun generateQuiz(): Quiz {
    val quizQuestions = mutableListOf<Question>()

    val questions = listOf(
        "What is the primary goal of Kotlin Multiplatform?",
        "How does Kotlin Multiplatform facilitate code sharing between platforms?",
        "Which platforms does Kotlin Multiplatform support?",
        "What is a common use case for Kotlin Multiplatform?",
        "What is a shared code module in Kotlin Multiplatform called?",
        "How does Kotlin Multiplatform handle platform-specific implementations?",
        "What languages can be interoperable with Kotlin Multiplatform?",
        "What tooling supports Kotlin Multiplatform development?",
        "What is the benefit of using Kotlin Multiplatform for mobile development?",
        "How does Kotlin Multiplatform differ from Kotlin Native and Kotlin/JS?"
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
            "Android, iOS, and web",
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
            "Shared module",
            "Kotlin file",
            "Code package",
            "Platform code"
        ),
        listOf(
            "Through expect and actual declarations",
            "By automatically translating code",
            "By restricting to a single platform",
            "By excluding platform-specific features"
        ),
        listOf(
            "Java, JavaScript, Swift",
            "C++, C#, Python",
            "HTML, CSS, Ruby",
            "Rust, TypeScript, Perl"
        ),
        listOf(
            "IntelliJ IDEA, Android Studio",
            "Eclipse, NetBeans",
            "Visual Studio Code",
            "Xcode"
        ),
        listOf(
            "Code reuse and sharing",
            "Improved performance",
            "Simplified UI development",
            "Enhanced debugging tools"
        ),
        listOf(
            "Kotlin Multiplatform allows sharing code between different platforms using common modules.",
            "Kotlin Native is exclusively for iOS development.",
            "Kotlin/JS is only for web development.",
            "Kotlin Multiplatform is entirely distinct from other Kotlin flavors."
        )
    )

    for (i in questions.indices) {
        val shuffledAnswers = answers[i].shuffled(Random(i))
        val correctAnswerId = shuffledAnswers.indexOfFirst { it == answers[i][0] } + 1
        val question = Question(i + 1L, questions[i], correctAnswerId, shuffledAnswers.mapIndexed { index, answer ->
            Answer(index + 1L, answer)
        })
        quizQuestions.add(question)
    }

    return Quiz(quizQuestions)
}
