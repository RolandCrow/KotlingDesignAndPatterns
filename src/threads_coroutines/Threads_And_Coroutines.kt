package threads_coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val t1 = measureTimeMillis {
            Blocking.profile("123")
        }
        val t2 = measureTimeMillis {
            Async().profile("123")
        }
        val t3 = measureTimeMillis {
            Suspend().profile("123")
        }
        println("Blocking code: $t1")
        println("Async: $t2")
        println("Suspend: $t3")
    }
}

class Blocking {
    companion object {
        fun profile(id: String): Profile {
            val bio = fetchBioOverHttp(id)
            val picture = fetchPictureFromDb(id)
            val friends = fetchFriendFromDb(id)
            return Profile(bio,picture, friends)
        }

        private fun fetchFriendFromDb(id: String): List<String> {
            Thread.sleep(500)
            return emptyList()
        }
        private fun fetchPictureFromDb(id: String): ByteArray? {
            Thread.sleep(100)
            return null
        }
        private fun fetchBioOverHttp(id: String): String {
            Thread.sleep(1000)
            return "_"
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
class Async {

    suspend fun profile(id: String): Profile {
        val bio = fetchBioOverHttpAsync(id)
        val picture = fetchPictureFromDbAsync(id)
        val friends = fetchFriendsFromDbAsync(id)
        return Profile(bio.await(),picture.await(),friends.await())
    }

    private fun fetchFriendsFromDbAsync(id: String) = GlobalScope.async {
        delay(500)
        emptyList<String>()
    }

    private fun fetchPictureFromDbAsync(id: String) = GlobalScope.async {
        delay(100)
        null
    }

    private fun fetchBioOverHttpAsync(id: String) = GlobalScope.async {
        delay(1000)
        "_"
    }
}

class Suspend {
    suspend fun profile(id: String): Profile {
        val bio = fetchBioOverHttp(id)
        val picture = fetchPictureFromDb(id)
        val friends = fetchFriendFromDb(id)
        return Profile(bio, picture, friends)
    }

    private suspend fun fetchFriendFromDb(id: String): List<String> {
        delay(500)
        return emptyList()
    }

    private suspend fun fetchPictureFromDb(id: String): ByteArray? {
        delay(100)
        return null
    }

    private suspend fun fetchBioOverHttp(id: String): String {
        delay(1000)
        return "_"
    }
}

data class Profile(val bio: String, val picture: ByteArray?, val friends: List<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Profile

        if (picture != null) {
            if (other.picture == null) return false
            if (!picture.contentEquals(other.picture)) return false
        } else if (other.picture != null) return false

        return true
    }

    override fun hashCode(): Int {
        return picture?.contentHashCode() ?: 0
    }
}