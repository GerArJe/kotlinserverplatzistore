package servidor

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resolveResource
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.route

fun Route.getImages() {
    route("/", HttpMethod.Get) {
        route("img", HttpMethod.Get) {
            route("/") {
                handle {
                    call.respondText(
                        "404",
                        ContentType.Text.Html,
                        HttpStatusCode.NotFound
                    )
                }
            }
            route("{image}"){
                handle {
                    val img = call.parameters["image"]
                    img?.let {
                        val imgResp = call.resolveResource("/images/$img")
                        if (imgResp!=null){
                            call.respond(imgResp)
                        }else{
                            call.respondText(
                                "404",
                                ContentType.Text.Html,
                                HttpStatusCode.NotFound
                            )
                        }
                    }
                }
            }
        }
    }
}