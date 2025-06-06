package functional_with_arrow

import arrow.core.merge
import arrow.fx.coroutines.raceN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

suspend fun main() {
    val winner: Pair<String,String> = raceN(
        { preciseWeather() },
        { weatherToday() }
    ).merge()
    println("winner $winner")
}

suspend fun weatherToday() = withContext(Dispatchers.IO) {
    delay(Random.nextLong(100))
    ("Weather Today" to "+24c")
}

suspend fun preciseWeather() = withContext(Dispatchers.IO) {
    delay(Random.nextLong(100))
    ("Precise Weather" to "+25c")
}

