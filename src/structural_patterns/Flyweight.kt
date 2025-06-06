package structural_patterns

import java.io.File

fun main() {
    val snails = List(10_000) {
        TansanianSnail()
    }
}

enum class Direction {
    LEFT,RIGHT
}

class TansanianSnail {
    private val directionFacing = Direction.LEFT

    private val sprites = SnailSprites.sprites
    fun getCurrentSprite() : File {
        return when(directionFacing) {
            Direction.LEFT -> sprites[0]
            Direction.RIGHT -> sprites[1]
        }
    }

}

object SnailSprites {
    val sprites = List(8) { i ->
        java.io.File(
            when(i) {
                0 -> "snail-left.jpg"
                1 -> "snail-right-jpg"
                in 2..4 -> "snail-move-left-${i-1}.jpg"
                else -> "snail-move-right${(4-i)}.jpg"
            }
        )
    }
}