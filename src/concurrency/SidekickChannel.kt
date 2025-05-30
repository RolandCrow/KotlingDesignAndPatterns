package concurrency

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

@OptIn(ObsoleteCoroutinesApi::class)
fun main() {
    runBlocking {
        val batman = actor<String> {
            for(c in channel) {
                println("Batman is beating some sense into $c")
                delay(100)
            }
        }

        val robin = actor<String> {
            for (c in channel) {
                println("Robin is beating some sense into $c")
            }
        }

        val epicFight = launch {
            for(villain in listOf("Joker", "Bane", "Penguin", "Riddler", "Killer Croc")) {
                val result =  select<Pair<String, String>> {
                    batman.onSend(villain) {
                        "Batman" to villain
                    }
                    robin.onSend(villain) {
                        "Robin" to villain
                    }
                }
                delay(90)
                println(result)
            }
        }
    }
}