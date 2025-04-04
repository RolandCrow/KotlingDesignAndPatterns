package structural_patterns


fun main() {
    cellPhone(
        charger(usPowerOutlet().toEUPlug()).toUsbTypeC()
    )
}

fun USPlug.toEUPlug() : EUPlug {
    val hasPower = if(this.hasPower == 1) "TRUE" else "FALSE"
    return object : EUPlug {
        override val hasPower: String = hasPower
    }
}

fun UsbMini.toUsbTypeC(): UsbTypeC {
    val hasPower = this.hasPower == Power.TRUE
    return object : UsbTypeC {
        override val hasPower: Boolean = hasPower
    }
}

fun usPowerOutlet(): USPlug {
    return object : USPlug {
        override val hasPower = 1
    }
}

fun charger(plug: EUPlug): UsbMini {
    return object : UsbMini {
        override val hasPower: Power = Power.valueOf(plug.hasPower)
    }
}

fun cellPhone(chargeCable: UsbTypeC) {
    if(chargeCable.hasPower) {
        println("I've Got The Power")
    } else {
        println("No power")
    }
}

interface USPlug {
    val hasPower: Int
}

interface EUPlug {
    val hasPower: String
}

interface UsbMini {
    val hasPower: Power
}

enum class Power {
    TRUE,FALSE
}

interface UsbTypeC {
    val hasPower: Boolean
}