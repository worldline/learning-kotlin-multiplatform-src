package data.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class RequestTime(val updateTime: Long = 0L, )