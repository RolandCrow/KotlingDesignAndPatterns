package structural_patterns


fun main() {
    val stormTrooper = StormTrooper(Rifle(),RegularLegs())
    val flameTrooper = StormTrooper(Flamethrower(),RegularLegs())
    val scoutTrooper = StormTrooper(Rifle(),AthleticLegs())

    println(listOf(stormTrooper,flameTrooper,scoutTrooper))
}

typealias PointsOfDamage = Long
typealias Meters = Int

interface Weapon {
    fun attack(x: Long, y: Long): PointsOfDamage
}

interface Legs {
    fun move(x:Long, y: Long): Meters
}

interface Trooper {
    fun move(x:Long, y: Long)
    fun attackRebel(x: Long, y: Long)
}

data class StormTrooper(
    private val weapon: Weapon,
    private val legs:Legs
): Trooper {
    override fun move(x: Long, y: Long) {
        legs.move(x,y)
    }

    override fun attackRebel(x: Long, y: Long) {
        println("Attacking")
        weapon.attack(x,y)
    }
}

const val RIFLE_DAMAGE = 3L
const val REGULAR_SPEED: Meters = 1

class Rifle: Weapon {
    override fun attack(x: Long, y: Long): PointsOfDamage = RIFLE_DAMAGE
}

class Flamethrower: Weapon {
    override fun attack(x: Long, y: Long): PointsOfDamage  = RIFLE_DAMAGE * 2
}

class Baton: Weapon {
    override fun attack(x: Long, y: Long): PointsOfDamage = RIFLE_DAMAGE * 3
}

class RegularLegs: Legs {
    override fun move(x: Long, y: Long): Meters = REGULAR_SPEED
}

class AthleticLegs: Legs {
    override fun move(x: Long, y: Long): Meters = REGULAR_SPEED * 2
}