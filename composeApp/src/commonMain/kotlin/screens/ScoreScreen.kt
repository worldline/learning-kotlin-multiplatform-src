package screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal fun scoreScreenPreview() {
    val onResetButtonPushed = {  }
    scoreScreen(onResetButtonPushed,score = "10", total = "10")
}

@Composable
internal fun scoreScreen(onResetButtonPushed: () -> Unit,score: String, total:String){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(10.dp),
            backgroundColor = generateScoringColor(score=score,total=total)

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            fontSize = 15.sp,
                            text = "score",
                        )
                        Text(
                            fontSize = 30.sp,
                            text = "$score/$total",
                        )

                        Button (
                            modifier = Modifier.padding(all = 20.dp),
                            onClick = {

                            }
                        ) {
                            Text(text = "Send the quiz result")
                        }

                        Button(
                            modifier = Modifier.padding(all = 20.dp),
                            onClick = {
                                onResetButtonPushed()
                            }
                        ) {
                            Icon(Icons.Filled.Refresh, contentDescription = "Localized description")
                            Text(text = "Retake the Quiz")
                        }
                    }
            }
        }
    }
}
private fun generateScoringColor(score:String,total: String): Color {
    val percentage = (score.toFloat() / total.toFloat()) * 100
    return when {
        percentage <= 40 -> Color.Red // red
        percentage in 41.0..69.0 -> Color.Yellow// orange
        else -> Color.Green// green
    }
}