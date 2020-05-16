package com.wust.game.playing.algorithm.jbosak

import algorithm.ai.makeMoveByAI
import algorithm.makeMove
import algorithm.shouldAIMakeMove
import com.google.gson.Gson
import createGame
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.ContentType
import io.ktor.http.cio.websocket.*
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.websocket.webSocket
import kotlinx.coroutines.delay
import lib.Gson.isValid
import lib.game.GameWithMove
import lib.game.Move
import lib.game.toGame
import lib.game.toGameView
import java.time.Duration

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
    }

    install(io.ktor.websocket.WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    val gson = Gson()

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
        webSocket("/createGame") {
            val game = createGame()
                .toGameView()
            outgoing.send(Frame.Text(gson.toJson(game)))
            for (frame in incoming) {

                val inputGame = handleIncomingFrame(frame, gson)
                if(inputGame != null){
                    when(inputGame.move){
                        is Move -> send(gson.toJson(makeMove(inputGame)))
                        null -> send(gson.toJson(makeMoveByAI(inputGame.board.toGame()).toGameView()))
                    }

                }
            }
        }
    }
}


fun handleIncomingFrame(frame:Frame, gson:Gson): GameWithMove? {
    return if(frame is Frame.Text){
        val json = frame.readText()
        val isValid = gson.isValid(json, GameWithMove::class.java)
        return if(isValid) gson.fromJson(json, GameWithMove::class.java) else null
    } else null

}


