package network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import network.data.Quiz

class QuizApiDatasource {
    private val httpClient = HttpClient {
        engine {

        }
        install(ContentNegotiation) {
            json(
                //contentType = ContentType.Text.Plain, // because Github is not returning an 'application/json' header
                json = Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
        }
    }
    suspend fun getAllQuestions(): Quiz {
        //https://awl.li/devoxxkmm2023
        return httpClient.get("http://localhost:9080/quiz").body()
    }
}
