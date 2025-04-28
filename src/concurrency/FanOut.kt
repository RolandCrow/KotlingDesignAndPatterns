package concurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

fun main() {
    runBlocking {
        val workChannel = generateWork()
        val workers = List(10) { id ->
            doWork(id, workChannel)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.generateWork(): ReceiveChannel<String> = produce {
    for(i in 1..10000) {
        send("page$i")
    }
    close()
}

fun CoroutineScope.doWork(
    id: Int,
    channel: ReceiveChannel<String>
) = launch(Dispatchers.Default)  {
    for(p in channel) {
        println("Worker $id processed $p")
    }
}