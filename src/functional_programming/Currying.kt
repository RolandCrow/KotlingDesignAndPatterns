package functional_programming

fun main() {
    println(subtract(4)(5))
    val infoLogger = createLogger(LogLevel.INFO)
    infoLogger("Log something")
}

fun subtract(x:Int, y: Int): Int {
    return x-y
}

fun subtract(x:Int) = {y: Int -> x - y}

enum class LogLevel {
    ERROR,WARNING,INFO
}

fun log(level: LogLevel, message: String) =
    println("$level: $message")

fun createLogger(level:LogLevel): (String) -> Unit {
    return {message: String ->
        log(level, message)
    }
}