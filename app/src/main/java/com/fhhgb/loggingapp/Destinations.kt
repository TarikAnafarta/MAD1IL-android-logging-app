package com.fhhgb.loggingapp

interface Routable {
    val route: String
}

object Overview : Routable {
    override val route: String = "main/overview"
}

object Person : Routable {
    override val route: String = "main/person"
}

object Sensor : Routable {
    override val route: String = "main/sensor"
}

object Intents : Routable {
    override val route: String = "main/intents"
}
