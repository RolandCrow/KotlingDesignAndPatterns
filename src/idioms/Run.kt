package idioms

import java.util.*

fun main() {
    val lowerCaseName = JamesBond().run {
        name = "ROGER MOORE"
        movie = "THE MAN WITH THE GOLDEN GUN"
        name.lowercase(Locale.getDefault())
    }
    println(lowerCaseName)
}