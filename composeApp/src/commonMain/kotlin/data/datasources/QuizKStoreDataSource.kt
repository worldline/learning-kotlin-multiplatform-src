package data.datasources

import data.dataclasses.Question
import data.dataclasses.Quiz
import getKStore
import io.github.xxfast.kstore.KStore
import kotlinx.datetime.Clock

class QuizKStoreDataSource {
    private val kStoreQuiz:KStore<Quiz>? = getKStore()
    suspend fun getUpdateTimeStamp():Long = kStoreQuiz?.get()?.updateTime ?: 0L

    suspend fun setUpdateTimeStamp(timeStamp:Long)  {
        kStoreQuiz?.update { quiz: Quiz? ->
            quiz?.copy(updateTime = timeStamp)
          }
    }
    suspend fun getAllQuestions(): List<Question> {
        return kStoreQuiz?.get()?.questions ?: emptyList()
     }

    suspend fun insertQuestions(newQuestions:List<Question>) {
        kStoreQuiz?.update { quiz: Quiz? ->
            quiz?.copy(questions = newQuestions)
          }
    }
    suspend fun resetQuizKstore(){
        kStoreQuiz?.delete()
        kStoreQuiz?.set(Quiz(emptyList(), 0L ))
    }
}