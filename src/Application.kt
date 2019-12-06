package com.example


import com.fasterxml.jackson.databind.SerializationFeature
import io.github.cdimascio.dotenv.dotenv
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import khttp.get
import khttp.responses.Response


val DOTENV = dotenv()
val TBA_API_KEY = DOTENV["TBA_AUTH_KEY"] ?: ""


fun getFromTba(endpoint: String): Response = get(
    url = "https://www.thebluealliance.com/api/v3/${endpoint.trimStart('/')}",
    headers = mapOf("X-TBA-Auth-Key" to TBA_API_KEY)
)

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        host("www.thebluealliance.com/api/v3")
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        get("/team/{teamNumber}") {
            val teamNumber = "${call.parameters["teamNumber"]}"
            call.respond(mapOf(teamNumber to String(getFromTba("/team/frc$teamNumber").content)))
        }
    }
}
