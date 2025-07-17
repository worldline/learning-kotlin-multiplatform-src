package com.worldline.quiz

import network.data.Answer
import network.data.Question
import network.data.Quiz
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


class QuizDbService {

    fun getQuiz(): Quiz {
        return transaction {
            val questions = mutableListOf<Question>()

            // Récupérer toutes les questions
            QuestionTable.selectAll().forEach { questionRow ->
                val questionId = questionRow[QuestionTable.id]
                val answers = mutableListOf<Answer>()

                // Récupérer toutes les réponses pour cette question
                AnswerTable.selectAll().forEach { answerRow ->
                    if (answerRow[AnswerTable.questionId] == questionId) {
                        answers.add(
                            Answer(
                                id = answerRow[AnswerTable.id],
                                label = answerRow[AnswerTable.label]
                            )
                        )
                    }
                }

                questions.add(
                    Question(
                        id = questionId,
                        label = questionRow[QuestionTable.label],
                        correctAnswerId = questionRow[QuestionTable.correctAnswerId],
                        answers = answers
                    )
                )
            }

            Quiz(questions)
        }
    }
}