package structural_patterns

fun main() {
    val starTrekRepository = DefaultStarTrekRepository()
    val withValidating = ValidatingAdd(starTrekRepository)
    val withLoggingAndValidating = LoggingGetCaptain(withValidating)

    withLoggingAndValidating["USS Enterprise"]

    try {
        withLoggingAndValidating["USS Voyager"] = "Kathryn Janeway"
    } catch (e: IllegalStateException) {
        println(e)
    }
}

interface StarTrekRepository {
    operator fun get(starshipName: String): String
    operator fun set(starshipName: String, captainName: String)
}

class DefaultStarTrekRepository: StarTrekRepository {
    private val starshipCaptain = mutableMapOf("USS Enterprise" to  "Jean-Luc Picard")
    override fun get(starshipName: String): String {
        return starshipCaptain[starshipName] ?:"Unknown"
    }

    override fun set(starshipName: String, captainName: String) {
        starshipCaptain[starshipName] = captainName
    }
}

class LoggingGetCaptain(private val repository: StarTrekRepository): StarTrekRepository by repository {
    override fun get(starshipName: String): String {
        println("Getting captain for $starshipName")
        return repository[starshipName]
    }
}

class ValidatingAdd(private val repository: StarTrekRepository): StarTrekRepository by repository {
    private val maxNameLength = 15
    override fun set(starshipName: String, captainName: String) {
        require(captainName.length < maxNameLength) {
            "$captainName name is longer than $maxNameLength characters!"
        }
        repository[starshipName] = captainName
    }
}

