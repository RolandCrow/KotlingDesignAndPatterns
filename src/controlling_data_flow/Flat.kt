package controlling_data_flow

fun main() {
    val listOfLists: List<List<Int>> = listOf(listOf(1,2), listOf(3,4,5), listOf(6,7,8))
    val flattered = mutableListOf<Int>()

    for(list in listOfLists) {
        flattered.addAll(list)
    }

    val flat: List<Int> = listOfLists.flatten()
    println(flat)
    println(listOfLists.flatten())
}