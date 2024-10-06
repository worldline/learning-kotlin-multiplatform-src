package data

import data.dataclasses.Question
import data.dataclasses.QuestionStats
import data.datasources.MockDataSource
import data.datasources.QuizApiDatasource
import data.datasources.QuizKStoreDataSource
import data.datasources.StatsApiDatasource
import kotlinx.datetime.Clock

class QuizRepository {

    private val mockDataSource = MockDataSource()
    private val quizApiDatasource = QuizApiDatasource()
    private var quizKStoreDataSource = QuizKStoreDataSource()

    private suspend fun fetchQuiz(): List<Question> = quizApiDatasource.getAllQuestions().questions

    /* FOR SPEAKER TALK DEMO ON WEB APP */ private val statsDataSource = StatsApiDatasource()

    private suspend fun fetchAndStoreQuiz(): List<Question> {
        quizKStoreDataSource.resetQuizKstore()
        val questions = fetchQuiz()
        quizKStoreDataSource.insertQuestions(questions)
        quizKStoreDataSource.setUpdateTimeStamp(Clock.System.now().epochSeconds)
        return questions
    }

    suspend fun updateQuiz(): List<Question> {
        try {
            val lastRequest = quizKStoreDataSource.getUpdateTimeStamp()
            return if (lastRequest == 0L || lastRequest - Clock.System.now().epochSeconds > 300000) {
                fetchAndStoreQuiz()
            } else {
                quizKStoreDataSource.getAllQuestions()
            }
        } catch (e: NullPointerException) {
            return fetchAndStoreQuiz()
        } catch (e: Exception) {
            e.printStackTrace()
            return mockDataSource.generateDummyQuestionsList()
        }
    }

    /* FOR SPEAKER TALK DEMO ON WEB APP */
    suspend fun storeStats(nickname: String, score: Int, responses: List<QuestionStats>) {
        statsDataSource.postQuestionStats(score, nickname, responses)
    }
}