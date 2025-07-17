package com.worldline.quiz


import com.worldline.quiz.plugins.generateQuiz
import io.ktor.server.application.Application
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import kotlin.getValue
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


fun Application.configureDatabaseH2() {
    Database.connect(
        url = "jdbc:h2:mem:test;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver"
    )

    transaction {

            // 1. Créer les tables
            SchemaUtils.create(QuestionTable, AnswerTable)

            // 2. Initialiser les données si nécessaire
            initializeQuizDataFromGenerated()

    }
}
    private fun initializeQuizDataFromGenerated() {
        // Vérifier si les données existent déjà
        if (QuestionTable.selectAll().count() > 0) {
            return
        }

        // Utiliser votre fonction generateQuiz() existante
        val quiz = generateQuiz()

        // Insérer chaque question et ses réponses
        quiz.questions.forEach { question ->
            // Insérer la question
            val questionId = QuestionTable.insert { stmt ->
                stmt[QuestionTable.label] = question.label
                stmt[QuestionTable.correctAnswerId] = question.correctAnswerId
            } get QuestionTable.id

            // Insérer toutes les réponses de cette question
            question.answers.forEach { answer ->
                AnswerTable.insert { stmt ->
                    stmt[AnswerTable.questionId] = questionId
                    stmt[AnswerTable.label] = answer.label
                }
            }
        }

}