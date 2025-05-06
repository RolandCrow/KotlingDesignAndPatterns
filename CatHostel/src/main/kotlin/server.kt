import cats.CatsServiceImpl
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(
        CIO,
        port = 8080,
        module = Application::mainModule
    ).start(wait = true)
}

fun Application.mainModule() {
    Db.connect()

    transaction {
        SchemaUtils.create(CatsTable)
    }

    install(ContentNegotiation) {
        json()
    }
    val catsService = CatsServiceImpl()
    routing {
        get("/status") {
            call.respond(mapOf("status" to "OK"),null)
        }
        cats(catsService)
    }
    println("open http://localhost:8080")
}