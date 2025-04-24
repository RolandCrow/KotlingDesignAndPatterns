package controlling_data_flow

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    buffered()
}

@OptIn(ObsoleteCoroutinesApi::class)
fun nonBuffered() {
    runBlocking {
        val actor = actor<Long> {
            var prev = 0L
            channel.consumeEach {
                println(it - prev)
                prev = it
                delay(100)
            }
        }
        repeat(10) {
            actor.send(System.currentTimeMillis())
        }
        actor.close().also { println("Done sending") }
    }
}

@OptIn(ObsoleteCoroutinesApi::class)
fun buffered() {
    runBlocking {
        val actor = actor<Long>(capacity = 10) {
            var prev = 0L
            channel.consumeEach {
                println(it - prev)
                prev = it
                delay(100)
            }
        }
        repeat(10) {
            actor.send(System.currentTimeMillis())
        }
        println("Done sending")
    }
}

