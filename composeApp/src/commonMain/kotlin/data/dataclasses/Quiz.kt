package data.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class Quiz(var questions: List<Question>,  val updateTime:Long=0L)