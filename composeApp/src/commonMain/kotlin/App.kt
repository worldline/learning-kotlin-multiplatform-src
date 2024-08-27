import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import screens.questionScreen
import screens.scoreScreen
import screens.welcomeScreen

@Composable
fun App(
    viewModel: QuizViewModel = viewModel { QuizViewModel() },
    navController: NavHostController = rememberNavController()
) {

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = "/welcome",
        ) {
            composable(route = "/welcome") {
                welcomeScreen(
                    onStartButtonPushed = {
                        navController.navigate(route = "/quiz")
                    }
                )
            }
            composable(route = "/quiz") {

                val questions by viewModel.questionState.collectAsState()

                if (questions.isNotEmpty()) {
                    questionScreen(
                        questions = questions,
                        onFinishButtonPushed = { score: Int, questionSize: Int ->

                            /* FOR SPEAKER TALK DEMO ON WEB APP */ if (getPlatform().toString() == "WASM") viewModel.postStats(
                            score,
                            "user-${(0..1000).random()}"
                        )
                            navController.navigate(route = "/score/$score/$questionSize")
                        },
                        /* FOR SPEAKER TALK DEMO ON WEB APP */
                        onSaveStatQuestion = { id: Long, question: String, anwserId: Long, correctAnwserId: Long, answer: String ->
                            viewModel.addStats(id, question, anwserId, correctAnwserId, answer)
                        }
                    )
                }
            }
            composable(route = "/score/{score}/{total}") {
                scoreScreen(
                    score = it.arguments?.getString("score").toString(),
                    total = it.arguments?.getString("total").toString(),
                    onResetButtonPushed = {
                        navController.navigate(route = "/quiz")
                    }
                )
            }

        }
    }
}