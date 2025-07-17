package data.datasources

import data.dataclasses.RequestTime
import io.github.xxfast.kstore.KStore

class KStoreDataSource(private val kStoreQuiz: KStore<RequestTime>?) {

    suspend fun getUpdateTimeStamp(): Long {
        return kStoreQuiz?.get()?.updateTime ?: kStoreQuiz?.set(RequestTime(0L)).let {
            0L
        }
    }

    suspend fun setUpdateTimeStamp(timeStamp: Long) {
        kStoreQuiz?.update { requestTime: RequestTime? ->
            requestTime?.copy(updateTime = timeStamp)
        }
    }
}