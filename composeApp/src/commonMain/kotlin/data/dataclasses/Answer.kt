package data.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class Answer(val id: Long, val label: String )