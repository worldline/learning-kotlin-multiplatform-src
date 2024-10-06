import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import screens.questionScreen
import screens.scoreScreen
import screens.welcomeScreen
import kotlin.random.Random
import kotlinx.serialization.Serializable

@Serializable
object WelcomeRoute

@Serializable
object QuizRoute

@Serializable
data class ScoreRoute(val score: Int, val questionSize: Int)

@Composable
fun App(
    viewModel: QuizViewModel = viewModel { QuizViewModel() },
    navController: NavHostController = rememberNavController()
) {

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = WelcomeRoute,
        ) {
            composable<WelcomeRoute>() {
                welcomeScreen(
                    onStartButtonPushed = {
                        navController.navigate(route = QuizRoute)
                    }
                )
            }
            composable<QuizRoute>() {
                val questions by viewModel.questionState.collectAsState()
                // random manga character names
                val nicknames = listOf(
                    "Naruto",
                    "Goku",
                    "Luffy",
                    "Ichigo",
                    "Saitama",
                    "Kenshin",
                    "Yusuke",
                    "Gon",
                    "Killua",
                    "Natsu",
                    "Gon",
                    "Gintoki",
                    "Koro-sensei",
                    "Kakashi",
                    "Vegeta",
                    "Sasuke",
                    "Zoro",
                    "Sanji",
                    "Shanks",
                    "Ace",
                    "Kaido",
                    "Katakuri",
                    "Law",
                    "Deku",
                    "Bakugo",
                    "Todoroki",
                    "All Might",
                    "Asta",
                    "Yuno",
                    "Yami",
                    "Julius",
                    "Licht",
                    "Nero",
                    "Grimm",
                    "Gordon",
                    "Yami",
                    "Magna",
                    "Luck",
                    "Noelle",
                    "Mimosa",
                    "Vanessa",
                    "Finral",
                    "Charmy",
                    "Gordon",
                    "Grey",
                    "Gimodelo",
                    "Zora",
                    "Mereoleona",
                    "Fuegoleon",
                    "Leopold",
                    "Kahono",
                    "Kiato",
                    "Yuno",
                    "Yami",
                    "Julius",
                    "Licht",
                    "Nero",
                    "Grimm",
                    "Gordon",
                    "Yami",
                    "Magna",
                    "Luck",
                    "Noelle",
                    "Mimosa",
                    "Vanessa",
                    "Finral",
                    "Charmy",
                    "Gordon",
                    "Grey",
                    "Gimodelo",
                    "Zora",
                    "Mereoleona",
                    "Fuegoleon",
                    "Leopold",
                    "Kahono",
                    "Kiato",
                    "Yuno",
                    "Yami",
                    "Julius",
                    "Licht",
                    "Nero",
                    "Grimm",
                    "Gordon",
                    "Yami",
                    "Magna",
                    "Luck",
                    "Noelle",
                    "Mimosa",
                    "Vanessa",
                    "Finral",
                    "Charmy",
                    "Gordon",
                    "Grey",
                    "Gimodelo",
                    "Zora",
                    "Mereoleona",
                    "Fuegoleon",
                    "Leopold",
                    "Kahono",
                    "Kiato",
                    "Yuno",
                    "Yami",
                    "Julius",
                    "Licht",
                    "Nero",
                    "Grimm",
                    "Gordon",
                    "Yami",
                    "Magna",
                    "Luck",
                    "Noelle",
                    "Mimosa",
                    "Vanessa",
                    "Finral",
                    "Charmy",
                    "Gordon",
                    "Grey",
                    "Gimodelo",
                    "Zora",
                    "Mereoleona",
                    "Fuegoleon"
                )

                if (questions.isNotEmpty()) {
                    questionScreen(
                        questions = questions,
                        onFinishButtonPushed = { score: Int, questionSize: Int ->
                            /* FOR SPEAKER TALK DEMO ON WEB APP */ if (getPlatform().name == "WASM") viewModel.postStats(
                            score,
                            "${nicknames.random()}-${Random.nextInt(1000)}"
                        )
                            navController.navigate(route = ScoreRoute(score, questionSize))
                        },
                        /* FOR SPEAKER TALK DEMO ON WEB APP */
                        onSaveStatQuestion = { id: Long, question: String, answerId: Long, correctAnswerId: Long, answer: String ->
                            viewModel.addStats(id, question, answerId, correctAnswerId, answer)
                        }
                    )
                }
            }
            composable<ScoreRoute> { backStackEntry ->
                val scoreRoute: ScoreRoute = backStackEntry.toRoute<ScoreRoute>()
                scoreScreen(
                    score = scoreRoute.score,
                    total = scoreRoute.questionSize,
                    onResetButtonPushed = {
                        navController.navigate(route = QuizRoute)
                    }
                )
            }
        }
    }
}