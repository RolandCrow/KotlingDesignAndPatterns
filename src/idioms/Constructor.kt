package idioms

fun main() {
    val c = User("Eugen")
    println(c.resetPassword)
}

class User(val name: String, val resetPassword: Boolean = true)