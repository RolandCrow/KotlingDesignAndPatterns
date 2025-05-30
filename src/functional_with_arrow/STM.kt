package functional_with_arrow

import arrow.fx.stm.*
import kotlin.random.Random

suspend fun main() {
    exampleTSet()
    exampleRetry()
}

suspend fun exampleRetry() {
    val letters = TArray.new('a','b','c')
    atomically {
        val letter = letters[Random.nextInt(letters.size())]
        println(letter)
        if(letter != 'a') {
            retry()
        }
    }
}

suspend fun exampleTSet() {
    val set = TSet.new<String>()
    try {
        atomically {
            set.insert("a")
            set.insert("b")
        }
        atomically {
            set.insert("c")
            set.insert("d")
            throw RuntimeException()
        }
    } catch (_: RuntimeException) {

    }
    atomically {
        println(set.member("a"))
        println(set.member("b"))
        println(set.member("c"))
        println(set.member("d"))
    }
}

fun STM.giveDonut(
    myBoxT: TVar<DonutBoxSTM>,
    yourBoxT: TVar<DonutBoxSTM>,
    donutName: String
) {
    addDonut(
        yourBoxT,
        removeDonut(
            myBoxT,
            donutName
        )
    )
}

fun STM.addDonut(boxT: TVar<DonutBoxSTM>,donut: Donut) {
    val yourBox = boxT.read()
    yourBox.add(donut)
    boxT.modify { yourBox }
}

fun STM.removeDonut(boxT: TVar<DonutBoxSTM>, donutName: String): Donut {
    val box = boxT.read()
    val donut = box.remove(donutName)
    requireNotNull(donut)

    boxT.modify { box }
    return donut
}

class DonutBoxSTM private constructor(
    private val donuts: MutableList<Donut> = mutableListOf()
) {
    companion object {
        suspend operator fun invoke(vararg donut: Donut): TVar<DonutBoxSTM> {
            return TVar.new(DonutBoxSTM(donut.toMutableList()))
        }
    }

    fun add(donut: Donut) {
        this.donuts.add(donut)
    }

    fun remove(donutName: String): Donut? {
        return this.donuts.find { it.name == donutName }?.let {
            donuts.remove(it)
            return it
        }
    }
    fun checkDonut(donutName: String): Donut? {
        return this.donuts.find { it.name == donutName }
    }
}