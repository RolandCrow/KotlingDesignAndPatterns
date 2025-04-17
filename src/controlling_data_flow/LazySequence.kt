package controlling_data_flow

import kotlin.system.measureTimeMillis

fun main() {
    val numbers = (1..1_000_000).toList()

    println(measureTimeMillis {
        numbers.map {
            it*it
        }.take(1).forEach{ _ -> }
    })

    println(measureTimeMillis {
        numbers.asSequence().map {
            it*it
        }.take(1).forEach { _-> }
    })

    println(measureTimeMillis {
        numbers.map {
            it*it
        }.forEach { _-> }
    })

    println(measureTimeMillis {
        numbers.asSequence().map {
            it*it
        }.forEach { _-> }
    })
}