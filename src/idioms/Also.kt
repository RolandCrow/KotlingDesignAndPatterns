package idioms

fun main() {
    val l = (1..100).toList()
    l.filter { it % 2 == 0 }
        .also { println(it) }
        .map { it * it}
}