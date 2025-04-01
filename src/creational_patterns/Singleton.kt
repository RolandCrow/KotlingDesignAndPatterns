package creational_patterns

fun main() {
    Logger.log("Hello World")
}

object Logger {
    init {
        println("I was accessed for the first time")
    }

    fun log(message: String){
        println("Logging $message")
    }
}