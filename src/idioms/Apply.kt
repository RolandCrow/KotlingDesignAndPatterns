package idioms

fun main() {
    val oo7 = JamesBond().apply {
        name = "Sean Connery"
        movie = "Dr. No"
    }
    println(oo7.name)
}

class JamesBond {
    lateinit var name: String
    lateinit var movie: String
    lateinit var alsoStarring: String
}