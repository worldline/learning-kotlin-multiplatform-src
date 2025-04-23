package data.datasources

import data.dataclasses.Quiz
import getPlatform
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.http.ContentType

val globalHttpClient = HttpClient {
    engine {

    }
    /*install(HttpCache) {
        privateStorage(
            storage = CacheStorage.Unlimited.invoke()
        )
    }*/

    install(ContentNegotiation) {
        json(
            contentType = ContentType.Text.Plain, // because Github is not returning an 'application/json' header
            json = Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
    }
}

class QuizApiDatasource {
    private val httpClient = globalHttpClient
    suspend fun getAllQuestions(): Quiz {
        return httpClient.get("https://raw.githubusercontent.com/worldline/learning-kotlin-multiplatform/main/quiz.json").body()
        /*val host =
            if (getPlatform().name == "WASM") "/quiz" else "https://ktor-quiz-qii6.onrender.com/quiz"
        return httpClient.get(host).body()*/
    }
}
