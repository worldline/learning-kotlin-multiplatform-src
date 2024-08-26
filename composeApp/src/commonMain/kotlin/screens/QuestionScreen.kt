package screens

import QuizViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import data.dataclasses.Question
import data.datasources.MockDataSource
import data.datasources.globalHttpClient
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun quizScreenPreview() {
    val onFinishButtonPushed = { _: Int, _: Int -> }
    questionScreen(onFinishButtonPushed, questions = MockDataSource().generateQuestionsList())
}

@Composable
internal fun questionScreen(onFinishButtonPushed: (Int, Int) -> Unit, questions: List<Question>) {
    val viewModel: QuizViewModel = viewModel { QuizViewModel() }
    var questionProgress by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf(1L) }
    var score by remember { mutableStateOf(0) }

    suspend fun sendQuizResult() {
        @Serializable
        data class QuestionResponse(
            val id: Long,
            val question: String,
            val anwserId: Long,
            val correctAnwserId: Long,
            val answer: String
        )

        @Serializable
        data class QuizResponse(val responses: List<QuestionResponse>, val score: Int, val nickname: String)

        val httpClient = globalHttpClient

        //val host = "http://localhost:8080"
        val host = "https://ybwl.alwaysdata.net"
        val body = QuizResponse(
            questions.map {
                QuestionResponse(
                    it.id,
                    it.label,
                    selectedAnswer,
                    it.correctAnswerId,
                    it.answers[selectedAnswer.toInt()].label
                )
            },
            score,
            "random-user-${(0..1000).random()}"
        )
        print("sending $body")
        httpClient.post {
            url("$host/respond")
            contentType(ContentType.Application.Json)
            setBody(body)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(Color(0xFFF5F5F5)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(60.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Text(
                    modifier = Modifier.padding(all = 10.dp),
                    text = questions[questionProgress].label,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
        Column(modifier = Modifier.selectableGroup()) {
            questions[questionProgress].answers.forEach { answer ->

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        modifier = Modifier.padding(end = 16.dp),
                        selected = (selectedAnswer == answer.id),
                        onClick = { selectedAnswer = answer.id },
                    )
                    Text(text = answer.label)
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier.padding(bottom = 20.dp),
                onClick = {
                    if (selectedAnswer == questions[questionProgress].correctAnswerId) {
                        score++
                    }
                    if (questionProgress < questions.size - 1) {
                        questionProgress++
                        selectedAnswer = 1
                    } else {
                        viewModel.viewModelScope.launch(Dispatchers.Default) {
                            sendQuizResult()
                        }
                        onFinishButtonPushed(score, questions.size)
                    }
                }
            ) {
                if (questionProgress < questions.size - 1) nextOrDoneButton(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    "Next"
                )
                else nextOrDoneButton(Icons.Filled.Done, "Done")
            }
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().height(20.dp),
                progress = questionProgress.div(questions.size.toFloat()).plus(1.div(questions.size.toFloat()))
            )
        }
    }
}

@Composable
internal fun nextOrDoneButton(iv: ImageVector, label: String) {
    Icon(
        iv,
        contentDescription = "Localized description",
        Modifier.padding(end = 15.dp)
    )
    Text(label)
}