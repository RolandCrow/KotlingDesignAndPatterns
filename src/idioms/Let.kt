package idioms

fun main() {
    val clintEastwoodQuotes = mapOf(
        "The Good, The Bad, The Ugly" to "Every gun makes its own tune.",
        "A Fistful Of Dollars" to "My mistake: four coffins."
    )
    val quote = clintEastwoodQuotes["Unforgiven"]
    if(quote != null) {
        println(quote)
    }

    clintEastwoodQuotes["A Fistful Of Dollars"]?.let {
        println(it)
    }

    clintEastwoodQuotes["Unforgiven"]?.let {
        println(it)
    }
}