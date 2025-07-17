import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.sqldelight.db.SqlDriver
import com.myapplication.common.cache.Database
import data.QuizRepository
import data.dataclasses.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class QuestionViewModel() : ViewModel() {

    private lateinit var quizRepository: QuizRepository
    private var _questionState = MutableStateFlow(listOf<Question>())
    var questionState: StateFlow<List<Question>> = _questionState

    init {
        viewModelScope.launch(Dispatchers.Main.immediate) {

            AppInitializer.init()
            quizRepository = QuizRepository()
            getQuestionQuiz()
        }
    }

    /* Can be replaced with explicit backing fields
    val questionState : StateFlow<List<Question>>
       field =  MutableStateFlow(listOf<Question>())
    -> in build.gradle.kts : sourceSets.all { languageSettings.enableLanguageFeature("ExplicitBackingFields") }
    */

    public fun getQuestionQuiz() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                _questionState.update {
                    quizRepository.updateQuiz()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}