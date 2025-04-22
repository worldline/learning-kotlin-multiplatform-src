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
                
                    questionScreen(

                        onFinishButtonPushed = { score: Int, questionSize: Int ->

                            navController.navigate(route = ScoreRoute(score, questionSize))
                        },

                    )

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