package functional_with_arrow

import arrow.core.raise.Raise
import java.util.Optional

fun main() {

}

data class Donut(
    val name: String,
    val calories:Int,
    val allergens: List<String> = listOf()
)
data class NoSuchDonut(val name: String)
data class DonutBoxRaise(
    val capacity: Int,
    val donuts: MutableList<Donut> = mutableListOf()
)
data class NonPositiveCapacity(val capacity: Int)

class DonutBox(private val capacity: Int) {
    private val donuts = mutableListOf<Donut>()
    fun addDonut(donut: Donut): DonutBox =  apply{
        if(donuts.size < capacity) {
            donuts.add(donut
            )
        } else {
            throw NoSpaceInBoxException()
        }
    }

    fun removeDonut(name: String): Donut? {
        return donuts.find { it.name == name }?.let {
            donuts.remove(it)
            it
        }
    }
 }

class DonutBoxOptional(private val capacity: Int){
    private val donuts = mutableListOf<Donut>()
    fun addDonut(donut: Donut): DonutBoxOptional = apply {
        if(donuts.size < capacity) {
            donuts.add(donut)
        } else {
            throw NoSpaceInBoxException()
        }
    }

    fun removeDonut(name: String): Optional<Donut> {
        return donuts.find { it.name== name }?.let {
            donuts.remove(it)
            Optional.of(it)
        } ?: Optional.empty()
    }
}

fun Raise<NoSpaceInBox>.addDonut(
    donutBox: DonutBoxRaise,
    donut: Donut
): DonutBoxRaise {
    if(donutBox.donuts.size < donutBox.capacity) {
        donutBox.donuts.add(donut)
        return donutBox
    } else {
        raise(NoSpaceInBox)
    }
}


sealed interface BoxError
data object NoSpaceInBox: BoxError
data object AlmostNoSpaceInBox: BoxError

class NoSuchDonutException: Throwable()
class NoSpaceInBoxException: RuntimeException("No space in box")