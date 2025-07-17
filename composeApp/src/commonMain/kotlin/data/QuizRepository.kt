package data

import data.dataclasses.Question
import data.datasources.MockDataSource
import data.datasources.QuizApiDatasource
import data.datasources.KStoreDataSource
import data.datasources.SqlDelightDataSource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class QuizRepository {
    private val mockDataSource = MockDataSource()
    private val quizApiDatasource = QuizApiDatasource()
    private var quizKStoreDataSource = KStoreDataSource(AppInitializer.getKStoreInstance())
    private var sqlDelightDataSource = SqlDelightDataSource(AppInitializer.getDatabase()!!)

    private suspend fun fetchQuiz(): List<Question> = quizApiDatasource.getAllQuestions().questions

    @OptIn(ExperimentalTime::class)
    private suspend fun fetchAndStoreQuiz(): List<Question> {
        sqlDelightDataSource.resetQuestions()

        val questions = fetchQuiz()
        sqlDelightDataSource.insertQuestions(questions)
        quizKStoreDataSource.setUpdateTimeStamp(Clock.System.now().epochSeconds)
        return questions
    }

    @OptIn(ExperimentalTime::class)
    suspend fun updateQuiz(): List<Question> {
        try {
            val lastRequest = quizKStoreDataSource.getUpdateTimeStamp()
            return if (lastRequest == 0L || lastRequest - Clock.System.now().epochSeconds > 300000) {
                fetchAndStoreQuiz()
            } else {
                //quizKStoreDataSource.getAllQuestions()
                sqlDelightDataSource.getAllQuestions()
            }
        } catch (e: NullPointerException) {
            return fetchAndStoreQuiz()
        } catch (e: Exception) {
            e.printStackTrace()
            return mockDataSource.generateDummyQuestionsList()
        }
    }

}