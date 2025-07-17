package com.worldline.quiz


import org.jetbrains.exposed.v1.core.Table


object QuestionTable : Table("question") {
    val id = long("id").autoIncrement()
    val label = varchar("label", 500)
    val correctAnswerId = long("correct_answer_id")

    override val primaryKey = PrimaryKey(id)
}

object AnswerTable : Table("answer") {
    val id = long("id").autoIncrement()
    val questionId = long("question_id").references(QuestionTable.id)
    val label = varchar("label", 500)

    override val primaryKey = PrimaryKey(id)
}