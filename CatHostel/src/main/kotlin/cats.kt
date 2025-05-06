import cats.CatsService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Routing.cats(service: CatsService) {
    route("/cats") {
        post {
            val parameters: Parameters = call.receiveParameters()
            val name = requireNotNull(parameters["name"])
            val age = parameters["age"]?.toInt() ?: 0
            service.create(name,age)
            call.respond(HttpStatusCode.Created, null)
        }
        get {
            val cats = service.findAll()
            call.respond(cats,null)
        }
        get("/{id}") {
            val id = requireNotNull(call.parameters["id"]).toInt()
            val cat = service.find(id)
            if(cat == null) {
                call.respond(HttpStatusCode.NotFound,null)
            } else {
                call.respond(cat, null)
            }
        }
    }
}