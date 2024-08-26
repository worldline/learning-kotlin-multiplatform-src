package data.datasources

import data.dataclasses.Quiz
import getPlatform
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

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
            //contentType = ContentType.Text.Plain, // because Github is not returning an 'application/json' header
            json = Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
    }
}

class QuizApiDatasource {
    private val httpClient = globalHttpClient
    suspend fun getAllQuestions(): Quiz {
        //return httpClient.get("https://raw.githubusercontent.com/worldline/learning-kotlin-multiplatform/main/quiz.json").body()
        val host =
            if (getPlatform().name == "Web with Kotlin JS") "/quiz" else "https://verbal-paula-yostane-5911cad1.koyeb.app/quiz"
        return httpClient.get(host).body()

    }
}
