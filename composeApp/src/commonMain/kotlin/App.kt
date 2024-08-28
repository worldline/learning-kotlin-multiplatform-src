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
import kotlin.random.Random

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
                // random manga character names
                val nicknames = listOf("Naruto", "Goku", "Luffy", "Ichigo", "Saitama", "Kenshin", "Yusuke", "Gon", "Killua", "Natsu",
                    "Gon", "Gintoki", "Koro-sensei", "Kakashi", "Vegeta", "Sasuke", "Zoro", "Sanji", "Shanks", "Ace", "Kaido", "Katakuri",
                    "Law", "Deku", "Bakugo", "Todoroki", "All Might", "Asta", "Yuno", "Yami", "Julius", "Licht", "Nero", "Grimm", "Gordon",
                    "Yami", "Magna", "Luck", "Noelle", "Mimosa", "Vanessa", "Finral", "Charmy", "Gordon", "Grey", "Gimodelo", "Zora", "Mereoleona",
                    "Fuegoleon", "Leopold", "Kahono", "Kiato", "Yuno", "Yami", "Julius", "Licht", "Nero", "Grimm", "Gordon", "Yami", "Magna",
                    "Luck", "Noelle", "Mimosa", "Vanessa", "Finral", "Charmy", "Gordon", "Grey", "Gimodelo", "Zora", "Mereoleona", "Fuegoleon",
                    "Leopold", "Kahono", "Kiato", "Yuno", "Yami", "Julius", "Licht", "Nero", "Grimm", "Gordon", "Yami", "Magna", "Luck", "Noelle",
                    "Mimosa", "Vanessa", "Finral", "Charmy", "Gordon", "Grey", "Gimodelo", "Zora", "Mereoleona", "Fuegoleon", "Leopold", "Kahono",
                    "Kiato", "Yuno", "Yami", "Julius", "Licht", "Nero", "Grimm", "Gordon", "Yami", "Magna", "Luck", "Noelle", "Mimosa", "Vanessa",
                    "Finral", "Charmy", "Gordon", "Grey", "Gimodelo", "Zora", "Mereoleona", "Fuegoleon")

                if (questions.isNotEmpty()) {
                    questionScreen(
                        questions = questions,
                        onFinishButtonPushed = { score: Int, questionSize: Int ->
                            /* FOR SPEAKER TALK DEMO ON WEB APP */ if (getPlatform().name == "WASM") viewModel.postStats(
                            score,
                            "${nicknames.random()}-${Random.nextInt(1000)}"
                        )
                            navController.navigate(route = "/score/$score/$questionSize")
                        },
                        /* FOR SPEAKER TALK DEMO ON WEB APP */
                        onSaveStatQuestion = { id: Long, question: String, answerId: Long, correctAnswerId: Long, answer: String ->
                            viewModel.addStats(id, question, answerId, correctAnswerId, answer)
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