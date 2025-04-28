package concurrency

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis


fun main() {
    runBlocking {
        println(measureTimeMillis {
            fetchFavoriteCharacterWrong("Inigo Montoya")
        })
        println(measureTimeMillis {
            fetchFavoriteCharacterCorrect("Inigo Montoya")
        })
        val (name,catchphrase, _) = fetchFavoriteCharacterCorrect("Inigp Montoya")
        val characters: List<Deferred<FavoriteCharacter>> =
            listOf(
                Me.getFavoriteCharacter(),
                Taylor.getFavoriteCharacter(),
                Michael.getFavoriteCharacter()
            )
        println(characters.awaitAll())
    }
}

data class FavoriteCharacter(
    val name: String,
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

suspend fun fetchFavoriteCharacterWrong(name: String) = coroutineScope {
    val catchphrase = getCatchphraseAsync(name).await()
    val picture = getPicture(name).await()
    FavoriteCharacter(name,catchphrase, picture)
}

suspend fun fetchFavoriteCharacterCorrect(
    name: String
) = coroutineScope {
    val catchphrase = getCatchphraseAsync(name)
    val picture = getPicture(name)
    FavoriteCharacter(name, catchphrase.await(), picture.await())
}

fun CoroutineScope.getCatchphraseAsync(
    characterName: String
) = async {
    delay(500)
    when(characterName) {
        "Inigo Montoya" -> "Hello my name is Inigo Montoya. You killed my father. Prepare to die."
        else -> "No catchphrase found"
    }
}

fun CoroutineScope.getPicture(
    characterName: String
) = async {
    delay(500)
    when (characterName) {
        else -> Random.nextBytes(42)
    }
}

object Michael {
    suspend fun getFavoriteCharacter() = coroutineScope {
        async {
            FavoriteCharacter("Terminator", "Hasta la vista, baby")
        }
    }
}

object Taylor {
    suspend fun getFavoriteCharacter() = coroutineScope {
        async {
            FavoriteCharacter("Don Vito Corleone", "I'm going to make him an offer he can't refuse")
        }
    }
}

object Me {
    suspend fun getFavoriteCharacter() = coroutineScope {
        async {
            FavoriteCharacter("Inigo Montoya","Hello, my name is...")
        }
    }
}
