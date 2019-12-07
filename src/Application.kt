package com.example


import com.fasterxml.jackson.databind.SerializationFeature
import io.github.cdimascio.dotenv.dotenv
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import khttp.get
import khttp.responses.Response


val DOTENV = dotenv()
val TBA_API_KEY = DOTENV["TBA_AUTH_KEY"] ?: ""
val TBA_API_ROOT = "www.thebluealliance.com/api/v3"


fun getFromTba(endpoint: String): Response = get(
    url = "https://${TBA_API_ROOT}/${endpoint.trimStart('/')}",
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
        host(TBA_API_ROOT)
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        get("/team/{teamNumber}") {
            val teamNumber = "${call.parameters["teamNumber"]}"
            call.respondText(
                String(getFromTba("/team/frc$teamNumber").content),
                contentType = ContentType.Application.Json
            )
        }
    }
}
