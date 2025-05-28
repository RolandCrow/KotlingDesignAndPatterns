package functional_with_arrow

fun main() {

}

sealed interface DonutIssue {
    data class AllergensPresent(val allergensList: Set<String>): DonutIssue {
        override fun toString(): String =
            "Allergens present $allergensList"

    }
    data class TooManyCalories(val max: Int, val given: Int): DonutIssue {
        override fun toString(): String =
            "Calories $given above maximum $max"
    }
}