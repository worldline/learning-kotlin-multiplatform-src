package data.datasources

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.myapplication.common.cache.Database
import data.dataclasses.Question
import data.dataclasses.Answer
import provideDbDriver

class SqlDelightDataSource(private val database: Database) {


    private var quizQueries = database.quizQuestionQueries

    suspend fun getAllQuestions(): List<Question> {
        return quizQueries.selectAllQuestionsWithAnswers().awaitAsList()

            .groupBy { it.question_id }
            .map { (questionId, rowList) ->

                Question(
                    id = questionId,
                    label = rowList.first().label,
                    correctAnswerId = rowList.first().correctAnswerId,
                    answers = rowList.map { answer ->
                        Answer(
                            id = answer.id_,
                            label = answer.label_
                        )
                    }
                )
            }
    }


    suspend fun insertQuestions(questions: List<Question>) {
        quizQueries.deleteQuestions();
        quizQueries.deleteAnswers()
        questions.forEach { question ->
            quizQueries.insertQuestion(question.id, question.label, question.correctAnswerId)
            question.answers.forEach { answer ->
                quizQueries.insertAnswer(answer.id, answer.label, question.id)
            }
        }
    }

    suspend fun resetQuestions() {
        quizQueries.deleteQuestions()
        quizQueries.deleteAnswers()
    }
}