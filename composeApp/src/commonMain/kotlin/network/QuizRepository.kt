package network

import app.cash.sqldelight.db.SqlDriver
import kotlinx.datetime.Clock
import network.data.Question


class QuizRepository(sqlDriver: SqlDriver)  {

    private val mockDataSource = MockDataSource()
    private val quizAPI = QuizApiDatasource()
    private var quizDB = QuizDbDataSource(sqlDriver)

    private suspend fun fetchQuiz(): List<Question> = quizAPI.getAllQuestions().questions

    private suspend fun fetchAndStoreQuiz(): List<Question>{
        val questions  = fetchQuiz()
        quizDB.insertQuestions(questions)
        quizDB.setUpdateTimeStamp(Clock.System.now().epochSeconds)
        return questions
    }
    
    suspend fun updateQuiz():List<Question>{
        try {
                    val lastRequest = quizDB.getUpdateTimeStamp()
                    if(lastRequest == 0L || lastRequest - Clock.System.now().epochSeconds > 300000){
                        return fetchAndStoreQuiz()

                    }else{
                        return quizDB.getAllQuestions()
                    }
                } catch (e: NullPointerException) {
                    return fetchAndStoreQuiz()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return mockDataSource.generateDummyQuestionsList()
                }

    }
    
}