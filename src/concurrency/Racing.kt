package concurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlin.random.Random

fun main() {
    runBlocking {
        while (true) {
            val winner = select {
                preciseWeather().onReceive { preciseWeatherResult ->
                    preciseWeatherResult
                }
                weatherToday().onReceive { weatherTodayResult ->
                    weatherTodayResult
                }
            }
            println(winner)
            delay(1000)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.preciseWeather() = produce {
    delay(Random.nextLong(100))
    send("Precise Weather" to "+25C")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.weatherToday() = produce {
    delay(Random.nextLong(100))
    send("Weather Today" to "+24C")
}