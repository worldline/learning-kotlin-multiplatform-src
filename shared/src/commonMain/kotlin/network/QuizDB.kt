package network


import app.cash.sqldelight.db.SqlDriver
import com.myapplication.common.cache.Database
import com.myapplication.common.cache.QuizDatabaseQueries
import network.data.Answer
import network.data.Question

class QuizDB(sqlDriver: SqlDriver) {

    private val database = Database(sqlDriver)
    private val quizQueries: QuizDatabaseQueries = database.quizDatabaseQueries

    suspend fun getUpdateTimeStamp():Long = quizQueries.selectUpdateTimestamp().executeAsOneOrNull()?.timestamprequest ?: 0L

    suspend fun setUpdateTimeStamp(timeStamp:Long)  {
        quizQueries.deleteTimeStamp()
        quizQueries.insertTimeStamp(timeStamp)
    }

        suspend fun getAllQuestions() = quizQueries.selectAllQuestions(
        mapper = { id, label, correctAnswer  ->
            Question(id,label,correctAnswer,getAnswersByQuestionId(id)
            )
        }).executeAsList()

     private  fun getAnswersByQuestionId(idQuestion:Long) = quizQueries.selectAllAnswersFromQuestion(
        questionId = idQuestion,
        mapper = { id, label, _ ->
            Answer(id, label)
        }).executeAsList()

    suspend fun insertQuestions(questions:List<Question>) {
        quizQueries.deleteQuestions();
        quizQueries.deleteAnswers()
        questions.forEach {question ->
            quizQueries.insertQuestion(question.id, question.label, question.correctAnswerId)
            question.answers.forEach {answer ->
                quizQueries.insertAnswer(answer.id,answer.label,question.id)
            }
        }
    }
}
