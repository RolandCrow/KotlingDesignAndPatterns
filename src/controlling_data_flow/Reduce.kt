package controlling_data_flow

fun main() {
    val numbers = 1..100
    var sum = 0
    for(n in numbers) {
        sum +=n
    }
    println(sum)

    val reduced: Int = (1..100).reduce{sums,n -> sums + n}
    println(reduced)
}