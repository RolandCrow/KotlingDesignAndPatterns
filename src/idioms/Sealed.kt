package idioms

import kotlin.random.Random

fun main() {
    var order: PizzaOrderStatus = OrderReceived(Random.nextInt())
    println(order)
    order = order.nextStatus()
    println(order)
    order = order.nextStatus()
    println(order)
    order = order.nextStatus()
    println(order)
}

sealed class PizzaOrderStatus(protected val orderId: Int) {
    abstract fun nextStatus():PizzaOrderStatus
}

class OrderReceived(orderId: Int): PizzaOrderStatus(orderId) {
    override fun nextStatus(): PizzaOrderStatus {
        return PizzaBeingMade(orderId)
    }
}

class PizzaBeingMade(orderId: Int): PizzaOrderStatus(orderId) {
    override fun nextStatus(): PizzaOrderStatus {
        return OutForDelivery(orderId)
    }
}

class OutForDelivery(orderId: Int): PizzaOrderStatus(orderId) {
    override fun nextStatus(): PizzaOrderStatus {
        return Completed(orderId)
    }
}

class Completed(orderId: Int): PizzaOrderStatus(orderId) {
    override fun nextStatus(): PizzaOrderStatus {
        return this
    }
}