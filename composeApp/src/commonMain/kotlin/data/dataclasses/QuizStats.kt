package data.dataclasses

import kotlinx.serialization.Serializable
import org.jetbrains.skia.impl.Stats

@Serializable
 /* FOR SPEAKER TALK DEMO ON WEB APP */ data class QuizStats(val responses: List<QuestionStats>, val score: Int, val nickname: String)

