package com.devoxxfr2023.km.network

import com.devoxxfr2023.km.network.data.Quiz
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class QuizAPI {
    private val httpClient = HttpClient() {



        install(ContentNegotiation) {
            json(
                contentType = ContentType.Text.Plain, // because Github is not returning an 'application/json' header

                json = Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
        }
    }
    suspend fun getAllQuestions(): Quiz {
        val result:Quiz = httpClient.get("https://github.com/worldline/learning-kotlin-multiplatform/raw/main/quiz.json"){
            headers {
                append(HttpHeaders.AccessControlAllowOrigin, "github.com")
                append( "Content-Type", "application/json")
                append("Access-Control-Allow-Origin", "*")
            }
        }.body()
        return result
    }
}