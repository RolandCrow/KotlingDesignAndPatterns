package controlling_data_flow

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.runBlocking

@OptIn(ObsoleteCoroutinesApi::class)
fun main() {
    runBlocking {
        val actor = actor<Int> {
            channel.consumeEach {
                println(it)
            }
        }
        (1..20).forEach {
            actor.send(it)
        }
    }
}