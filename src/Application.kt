package com.example

import com.example.modules.Customer
import io.ktor.application.*
import io.ktor.content.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(StatusPages) {
        exception<Throwable> { e ->
            call.respondText("Invalid Customer Info provided", ContentType.Text.Plain, HttpStatusCode.BadRequest)
        }
        status(HttpStatusCode.NotFound) {
            call.respond(TextContent("${it.value} ${it.description}", ContentType.Text.Plain.withCharset(Charsets.UTF_8), it))
        }
    }

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        post("/register") {
            val customer = call.receive<Customer>()
            val uuid = UUID.randomUUID().toString()
            val dateTime = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            val magicNo = (0..100).random()

            call.respond(HttpStatusCode.Created, Customer(customer.username, customer.email, customer.bio, customer.city, uuid, dateTime, magicNo))
        }
    }

    install(ContentNegotiation) {
        gson()
    }
}
