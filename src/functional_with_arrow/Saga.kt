package functional_with_arrow

import arrow.resilience.saga
import arrow.resilience.transact

suspend fun main() {
    val box = DonutBoxSaga()
    val sendDonutSaga = saga {
        saga ({
            putDonuts(box)
        }){
            unpack(box)
        }
        saga({
            addLabel(box)
        }) {
            removeLabel(box)
        }
        saga({
            passToCourier(box)
        }) {
            println("I wasted so much time and the courier never came!")
        }
    }
    try {
        sendDonutSaga.transact()
    }catch (e: Exception) {
        println("Failed saga ${e.message}")
    }
}

fun passToCourier(box: DonutBoxSaga)  {
    throw RuntimeException("Courier never came!")
}

fun removeLabel(box: DonutBoxSaga) {
    println("Removing the label")
}

fun addLabel(box: DonutBoxSaga) {
    println("Adding label to the box")
}

fun unpack(box: DonutBoxSaga) {
    println("Putting donuts back on the counter")
}

fun putDonuts(box: DonutBoxSaga) {
    println("Putting donuts in a box")
}

class DonutBoxSaga {}