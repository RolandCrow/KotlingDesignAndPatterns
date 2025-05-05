package idioms

import kotlin.reflect.KClass

fun main() {
    printIfSameType(Int::class,1)
    printIfSameType(Int::class,2L)
    printIfSameType(Long::class, 3L)

    printIfSameReified<Int>(1)
    printIfSameReified<Int>(2L)
    printIfSameReified<Long>(3L)

    val p: Pair<String,String> = "a" to "b"
    printList(p.toList())
}

fun <T: Number> printIfSameType(clazz: KClass<T>, a: Number) {
    if(clazz.isInstance(a)) {
        println("Yes")
    } else {
        println("No")
    }
}

inline fun <reified T: Number> printIfSameReified(a: Number) {
    if(a is T) {
        println("Yes")
    } else {
        println("No")
    }
}

inline fun <reified T: Any> printList(list: List<T>){
    when {
        1 is T -> println("This is a list of Ints")
        1L is T -> println("This is a list of Longs")
        else ->  println("This is a list of something else")
    }
}

