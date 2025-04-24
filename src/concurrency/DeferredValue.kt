package concurrency

import kotlinx.coroutines.*
import kotlin.random.Random


fun main() {
    runBlocking {
        val value = valueAsync()
        println(value.await())
    }
}

suspend fun valueAsync(): Deferred<String> = coroutineScope {
    val deferred = CompletableDeferred<String>()
    launch {
        delay(100)
        if(Random.nextBoolean()) {
            deferred.complete("Ok")
        } else {
            deferred.completeExceptionally(
                RuntimeException()
            )
        }
    }
    deferred
}