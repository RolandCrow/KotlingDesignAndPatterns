package functional_with_arrow

import arrow.fx.coroutines.CyclicBarrier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    val barrier = CyclicBarrier(3)
    runBlocking(Dispatchers.IO) {
        ('a'..'x').forEachIndexed { _, letter ->
            launch {
               fetchAsync("https://en.wikipedia.org/wiki/$letter")
                barrier.await()
                println("Fetched letter $letter at ${System.currentTimeMillis() % 1000}")
            }
        }
    }
}

