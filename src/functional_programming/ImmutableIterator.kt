package functional_programming

fun main() {
    printAndClear(mutableListOf("a","b","c"))
}

private fun <T> printAndClear(list: MutableList<T>): MutableList<T> {
    for(e in list) {
        println(e)
    }
    return mutableListOf()
}