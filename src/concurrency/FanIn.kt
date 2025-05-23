package concurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val workChannel = generateWork()
        val resultChannel = Channel<String>()
        val workers = List(10) {
            doWorkAsync(workChannel,resultChannel)
        }
        resultChannel.consumeEach {
            println(it)
        }
    }
}

private fun CoroutineScope.doWorkAsync(
    channel: ReceiveChannel<String>,
    receiveChannel: Channel<String>
)  = async(Dispatchers.Default) {
    for(p in channel) {
        receiveChannel.send(p.repeat(2))
    }
}