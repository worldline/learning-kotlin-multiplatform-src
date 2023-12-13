package network.data

public data class SelectQuestionWithAnswers(

    val id:Long,
    val label:String,
    val questionId: Long,
    val correctAnswerId:Long,
    val questionLabel: String

)
