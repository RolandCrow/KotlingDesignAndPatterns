package threads_coroutines

import kotlinx.coroutines.*
import java.util.UUID

fun main() {
    runBlocking {
        val job: Deferred<UUID> = fastUuidAsync()
        println(job.await())
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun fastUuidAsync() = GlobalScope.async {
    UUID.randomUUID()
}

@OptIn(DelicateCoroutinesApi::class)
fun slowUuidAsync() = GlobalScope.async {
    repeat(10_000_000) {
        UUID.randomUUID()
    }
    UUID.randomUUID()
}

