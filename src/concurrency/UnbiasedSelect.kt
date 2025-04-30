package concurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.selectUnbiased

fun main() {
    runBlocking {
        val firstOption = fastProducer("Quick&Angry 7")
        val secondOption = fastProducer("Revengers: Penultimatum")

        delay(10)
        val movieToWatch = selectUnbiased {
            firstOption.onReceive {it}
            secondOption.onReceive {it}
        }
        println(movieToWatch)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.fastProducer(
    movieName: String
) = produce(capacity = 1) {
    send(movieName)
}