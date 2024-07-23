
import androidx.compose.material.Text
import com.worldline.quiz.cache.Database
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import app.cash.sqldelight.async.coroutines.awaitCreate
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import screens.welcomeScreen
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.w3c.dom.Element


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    @OptIn(ExperimentalComposeUiApi::class)
    fun main() {
        CanvasBasedWindow(canvasElementId = "ComposeTarget") {
           Text("HelloWorld")
        }
    }
}