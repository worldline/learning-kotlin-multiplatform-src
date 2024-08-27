package data.dataclasses

import kotlinx.serialization.Serializable

@Serializable
/* FOR SPEAKER TALK DEMO ON WEB APP */ data class QuestionStats(
    val question: String,
    val answer: String,
    val answerId: Long,
    val id: Long,
    val correctAnswerId: Long
)