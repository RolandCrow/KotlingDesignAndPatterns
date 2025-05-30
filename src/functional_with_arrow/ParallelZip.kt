package functional_with_arrow

import arrow.fx.coroutines.parZip
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.system.measureTimeMillis

suspend fun main() {
    val t = measureTimeMillis {
       parZip(
           {Me.getFavoriteCharacter().await()},
           {Taylor.getFavoriteCharacter().await()},
           {Michael.getFavorite().await()}
       ) { me, taylor,michael ->
           println("Favorite characters are: $me,$taylor,$michael")
       }
    }
    println("Took ${t}ms")
}


object Michael {
    suspend fun getFavorite() = coroutineScope {
        async {
            delay(300)
            FavoriteCharacter("Terminator","Hasta la vista, baby")
        }
    }
}

object Taylor {
    suspend fun getFavoriteCharacter() = coroutineScope {
        async {
            delay(200)
            FavoriteCharacter("Don Vito Corleone", "I'm going to make him an offer he can't refuse")
        }
    }
}

object Me {
    suspend fun getFavoriteCharacter() = coroutineScope {
        async {
            delay(200)
            FavoriteCharacter("Inigo Montoya", "Hello, my name is...")
        }
    }
}

data class FavoriteCharacter(
    val name:String,
    val catchphrase: String,
    val picture: ByteArray = Random.nextBytes(42)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as FavoriteCharacter
        return picture.contentEquals(other.picture)
    }

    override fun hashCode(): Int {
        return picture.contentHashCode()
    }
}