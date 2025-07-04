package com.worldline.quiz.plugins


import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.modelcontextprotocol.kotlin.sdk.CallToolResult
import io.modelcontextprotocol.kotlin.sdk.GetPromptResult
import io.modelcontextprotocol.kotlin.sdk.Implementation
import io.modelcontextprotocol.kotlin.sdk.PromptArgument
import io.modelcontextprotocol.kotlin.sdk.PromptMessage
import io.modelcontextprotocol.kotlin.sdk.ReadResourceResult
import io.modelcontextprotocol.kotlin.sdk.Role
import io.modelcontextprotocol.kotlin.sdk.ServerCapabilities
import io.modelcontextprotocol.kotlin.sdk.TextContent
import io.modelcontextprotocol.kotlin.sdk.TextResourceContents
import io.modelcontextprotocol.kotlin.sdk.Tool
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.mcp
import kotlinx.coroutines.runBlocking

fun configureMcpServer(): Server {
    val server = Server(
        Implementation(
            name = "mcp-kotlin quiz server",
            version = "0.1.0"
        ),
        ServerOptions(
            capabilities = ServerCapabilities(
                //prompts = ServerCapabilities.Prompts(listChanged = true),
                //resources = ServerCapabilities.Resources(subscribe = true, listChanged = true),
                tools = ServerCapabilities.Tools(listChanged = true),
            )
        )
    )



    /*server.addPrompt(
        name = "Kotlin Developer",
        description = "Develop small kotlin applications",
        arguments = listOf(
            PromptArgument(
                name = "Project Name",
                description = "Project name for the new project",
                required = true
            )
        )
    ) { request ->
        GetPromptResult(
            "Description for ${request.name}",
            messages = listOf(
                PromptMessage(
                    role = Role.user,
                    content = TextContent(
                        "Develop a kotlin project named <name>${
                            request.arguments?.get(
                                "Project Name"
                            )
                        }</name>"
                    )
                )
            )
        )
    }*/

    // Add a tool
    server.addTool(
        name = "kotlin-sdk-quiz-tool",
        description = "a tool to retrieve questions and answers for the quiz about kotlin multiplatform",
        inputSchema = Tool.Input()
    ) { request ->
        CallToolResult(
            content = listOf(TextContent("Here is a quiz about Kotlin Multiplatform! ${generateQuiz().questions.toString()}")),
        )
    }

    // Add a resource
   /* server.addResource(
        uri = "https://search.com/",
        name = "Web Search",
        description = "Web search engine",
        mimeType = "text/html"
    ) { request ->
        ReadResourceResult(
            contents = listOf(
                TextResourceContents(
                    "Placeholder content for ${request.uri}",
                    request.uri,
                    "text/html"
                )
            )
        )
    }*/

    return server
}

fun runSseMcpServerUsingKtorPlugin(port: Int): Unit = runBlocking {

    println("Starting sse server on port $port")
    println("Use inspector to connect to the http://localhost:$port/sse")

    embeddedServer(CIO, host = "0.0.0.0", port = port) {
        mcp {
            return@mcp configureMcpServer()
        }
    }.start(wait = true)
}