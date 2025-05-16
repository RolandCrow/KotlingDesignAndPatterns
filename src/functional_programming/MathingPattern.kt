package functional_programming

fun main() {
    println(getSound(Cat()))
    println(getSound(Dog()))

    try {
        println(getSound(Crow()))
    }
    catch (e: IllegalStateException) {
        println(e)
    }
}

interface Animal

class Crow: Animal {
    fun caw(): String {
        return "Caw Caw!"
    }
}

class Dog: Animal {
    fun bark(): String {
        return "Bark-Bark"
    }
}

class Cat: Animal {
    fun purr(): String {
        return "Purr-Purr"
    }
}

fun getSound(animal: Animal): String {
    var sound: String? = null
    if(animal is Cat) {
        sound = animal.purr()
    } else if(animal is Dog) {
        sound = animal.bark()
    }
    checkNotNull(sound)
    return sound
}