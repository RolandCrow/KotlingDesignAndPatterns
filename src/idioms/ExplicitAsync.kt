package idioms

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        println(getResultAsync().await())
    }
}

fun CoroutineScope.getResultAsync() = async {
    delay(100)
    "OK"
}