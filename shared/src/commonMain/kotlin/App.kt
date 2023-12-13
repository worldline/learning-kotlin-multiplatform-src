
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import app.cash.sqldelight.db.SqlDriver
import org.jetbrains.compose.resources.ExperimentalResourceApi



@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(sqlDriver: SqlDriver) {



    MaterialTheme {

        //scoreScreen("10/10")
        /*var questions = listOf(
            Question(
                1,
                "Android is a great platform ?",
                1,
                listOf(Answer(1, "YES"), Answer(2, "NO"))
            ),
            Question(
                1,
                "Android is a bad platform ?",
                2,
                listOf(Answer(1, "YES"), Answer(2, "NO"))
            )
        )*/

        rootNavHost(sqlDriver)

        /*val questions = repository.questionState.collectAsState()

        if(questions.value.isNotEmpty()) {
            questionScreen(questions.value)
        }*/


        /*var greetingText by remember { mutableStateOf("Hello, World!") }
        var showImage by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                greetingText = "Hello, ${getPlatformName()}"
                showImage = !showImage
            }) {
                Text(greetingText)
            }
            AnimatedVisibility(showImage) {
                Image(
                    painterResource("compose-multiplatform.xml"),
                    contentDescription = "Compose Multiplatform icon"
                )
            }*/
        }
    }

expect fun getPlatformName(): String

expect class DatabaseDriverFactory {
     fun createDriver(): SqlDriver
}