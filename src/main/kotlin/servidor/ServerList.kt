package servidor

import com.google.gson.GsonBuilder
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.route

fun Route.listProducts() {
    route("/list", HttpMethod.Get) {
        val productos = mutableListOf<Producto>()
        (0..20).map {
            productos.add(
                Producto(
                    (Math.random() * 1000).toInt(),
                    "Jacket",
                    "Jacket para el frio",
                    "sadsfdgfhgjhkjhdsfdsfsdfsdfdsfsd",
                    200.00 + it,
                    "http://localhost:8080/img/jacket.jpg"
                )
            )

            productos.add(
                Producto(
                    (Math.random() * 1000).toInt(),
                    "Hoodie",
                    "Hoodie para verte super cool",
                    "sadsfdgfhgjhkjhdsfdsfsdfsdfdsfsd",
                    200.00 + it,
                    "http://localhost:8080/img/hoodie.jpg"
                )
            )

            productos.add(
                Producto(
                    (Math.random() * 1000).toInt(),
                    "Cup",
                    "Para una buena taza de cafe",
                    "sadsfdgfhgjhkjhdsfdsfsdfsdfdsfsd",
                    200.00 + it,
                    "http://localhost:8080/img/cup.jpg"
                )
            )

            productos.add(
                Producto(
                    (Math.random() * 1000).toInt(),
                    "t-shirt",
                    "sientete comodo",
                    "sadsfdgfhgjhkjhdsfdsfsdfsdfdsfsd",
                    200.00 + it,
                    "http://localhost:8080/img/t-shirt.jpg"
                )
            )
        }
        val playload = ResponseJson(payload = productos, statusCode = HttpStatusCode.OK.value)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonRs = gson.toJson(playload)

        handle {
            call.respondText(jsonRs, ContentType.Application.Json, HttpStatusCode.OK)
        }
    }
}