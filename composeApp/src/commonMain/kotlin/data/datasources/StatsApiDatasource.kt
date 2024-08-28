package data.datasources

import data.dataclasses.Question
import data.dataclasses.QuestionStats
import data.dataclasses.Quiz
import data.dataclasses.QuizStats
import getPlatform
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json



/* FOR SPEAKER TALK DEMO ON WEB APP */
class StatsApiDatasource {
    private val httpClient = globalHttpClient
    
    
    suspend fun postQuestionStats(score:Int,nickname:String,responses:List<QuestionStats>) {
        
        val host = "https://ybwl.alwaysdata.net"
        val body = QuizStats(
            responses = responses,
            score=score,
            nickname = nickname
        )
        
       httpClient.post {
           url("$host/respond")
           contentType(ContentType.Application.Json)
           setBody(body)
       }

    }
}

