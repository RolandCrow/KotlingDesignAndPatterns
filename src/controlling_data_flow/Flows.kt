package controlling_data_flow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch

fun main() {
    runBlocking {
        val numberFlow: Flow<Int> = flow {
            println("New subscriber")
            (1..10).forEach {
                println("Sending $it")
                emit(it)
                if (it == 9) {
                    throw RuntimeException()
                }
            }
        }

        (1..4).forEach { coroutineId ->
            delay(5000)
            launch(Dispatchers.Default) {
                try {
                    numberFlow.collect {number ->
                        delay(1000)
                        println("Coroutine $coroutineId received $number")
                    }
                } catch (e: Exception) {
                    println("Coroutine $coroutineId got an error")
                }
            }
        }
    }
}
