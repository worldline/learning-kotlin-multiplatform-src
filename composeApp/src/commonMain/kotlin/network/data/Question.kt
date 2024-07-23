package network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Question(val id:Long, val label:String, @SerialName("correct_answer_id") val correctAnswerId:Long, val answers:List<Answer>)