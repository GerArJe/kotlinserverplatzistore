package servidor

import com.google.gson.GsonBuilder
import io.ktor.application.*
import io.ktor.features.CallLogging
import io.ktor.http.ContentType
import io.ktor.http.DEFAULT_PORT
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.netty.handler.codec.DefaultHeaders
import java.lang.NumberFormatException
import javax.sound.sampled.Port

fun Application.main() {
    install(io.ktor.features.DefaultHeaders)
    install(CallLogging)

    routing {
        get("/me") {
            val name = call.request.queryParameters["name"]
            val age = call.request.queryParameters["age"]

            val gson = GsonBuilder().setPrettyPrinting().create()

            if (name != null && age != null) {
                val edad = try {
                    age.toInt()
                }catch (e:NumberFormatException){
                    0
                }
                val me = Persona(name, edad)

                val jsonRes = gson.toJson(me)
                call.respondText(jsonRes, ContentType.Application.Json, HttpStatusCode.OK)
            } else {
                val error = Error(HttpStatusCode.BadRequest.value, "Datos invalidos")
                call.respondText(gson.toJson(error), ContentType.Application.Json, HttpStatusCode.BadRequest)
            }
        }

        post("/postme") {
            val messagepost = call.receiveText()
            println(messagepost)
            call.respondText("ok", ContentType.Text.Html, HttpStatusCode.OK)
        }
    }
}

fun main() {
    embeddedServer(Netty, 8080, watchPaths = listOf("AppServerKt"), module = Application::main).start()
}