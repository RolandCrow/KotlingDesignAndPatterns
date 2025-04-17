package threads_coroutines

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    runBlocking {
        launch {
            println(Thread.currentThread().name)
        }
        GlobalScope.launch {
            println("GlobalScope.launch: ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) {
            println(Thread.currentThread().name)
        }
    }
}