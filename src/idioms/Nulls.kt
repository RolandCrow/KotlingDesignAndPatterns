package idioms

import kotlin.random.Random

fun main() {

    val stringOrNull: String? = if(Random.nextBoolean()) "String" else null
    if(stringOrNull != null) {
        println(stringOrNull.length)
    }
    val alwaysLength = stringOrNull?.length ?: 0
    val response = Response(UserProfile(null,null))
    println(response.profile?.firstName?.length)
}

data class Response(
    val profile: UserProfile?
)

data class UserProfile(
    val firstName: String?,
    val lastName: String?
)