package behavioral_patterns

fun main() {
    val catTheConductor = Cat()

    val bat = Bat()
    val dog = Dog()
    val turkey = Turkey()

    catTheConductor.joinChoir(bat::screech)
    catTheConductor.joinChoir(dog::howl)
    catTheConductor.joinChoir(dog::bark)
    catTheConductor.joinChoir(turkey::gobble)

    catTheConductor.conduct()
    catTheConductor.conduct()
}

class Bat {
    fun screech() {
        println("Eeeeeeee")
    }
}

class Turkey {
    fun gobble() {
        println("Gob-gob")
    }
}

class Dog {
    fun bark() {
        println("Woof")
    }
    fun howl() {
        println("Auuuu")
    }
}

class Cat {
    private val participants = mutableMapOf<() -> Unit,()-> Unit>()

    fun joinChoir(whatToCall: () -> Unit) {
        participants[whatToCall] = whatToCall
    }

    fun leaveChoir(whatToCall: () -> Unit) {
        participants.remove(whatToCall)
    }

    fun conduct() {
        for(p in participants.values) {
            p()
        }
    }
}

typealias Times = Int

enum class SouthPitch {HIGH,LOW}
interface Message {
    val repeat: Times
    val pitch: SouthPitch
}

data class LowMessage(override val repeat: Times): Message {
    override val pitch = SouthPitch.LOW
}

data class HighMessage(override val repeat: Times): Message {
    override val pitch = SouthPitch.HIGH
}
