package com.worldline.quiz

import com.worldline.quiz.plugins.configureRouting
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import  io.ktor.server.plugins.cors.routing.CORS

fun main() {
    embeddedServer(Netty, port = 9091, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}


fun Application.module() {

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.ContentType)
        anyHost()
    }

    install(ContentNegotiation) {
        json()
    }
    configureRouting()
}