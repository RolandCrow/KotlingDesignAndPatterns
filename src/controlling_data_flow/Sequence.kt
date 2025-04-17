package controlling_data_flow

fun main() {
    val seq: Sequence<Long> = generateSequence(1L) { it + 1}

    (1..100).asSequence()
    val fibSeq = sequence {
        var a = 0
        var b = 1
        yield(a)
        yield(b)
        while (true) {
            yield(a+b)
            val t = a
            a = b
            b += t
        }
    }
    fibSeq.take(10).forEach {
        println(it)
    }
}