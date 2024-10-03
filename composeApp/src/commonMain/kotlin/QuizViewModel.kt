import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.QuizRepository
import data.dataclasses.Question
import data.dataclasses.QuestionStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class QuizViewModel : ViewModel() {
    private var quizRepository: QuizRepository = QuizRepository()
    private var _questionState = MutableStateFlow(listOf<Question>())
    var questionState: StateFlow<List<Question>> = _questionState

    /* Can be replaced with explicit backing fields
    val questionState : StateFlow<List<Question>>
       field =  MutableStateFlow(listOf<Question>())
    -> in build.gradle.kts : sourceSets.all { languageSettings.enableLanguageFeature("ExplicitBackingFields") }
    */

    /* FOR SPEAKER TALK DEMO ON WEB APP */ private var questionStatsList: ArrayList<QuestionStats> =
        ArrayList<QuestionStats>()

    init {
        getQuestionQuiz()
    }

    private fun getQuestionQuiz() {

        viewModelScope.launch(Dispatchers.Default) {
            _questionState.update {
                quizRepository.updateQuiz()
            }
        }
    }

    /* FOR SPEAKER TALK DEMO ON WEB APP */
    public fun addStats(id: Long, question: String, answerId: Long, correctAnswerId: Long, answer: String) {
        questionStatsList.add(
            QuestionStats(
                id = id,
                question = question,
                answerId = answerId,
                correctAnswerId = correctAnswerId,
                answer = answer
            )
        )
    }

    /* FOR SPEAKER TALK DEMO ON WEB APP */
    public fun postStats(score: Int, nickName: String) {
        viewModelScope.launch(Dispatchers.Default) {
            quizRepository.storeStats(nickName, score, questionStatsList)
        }
    }
}