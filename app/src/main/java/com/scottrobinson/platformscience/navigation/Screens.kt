package com.scottrobinson.platformscience.navigation

import kotlinx.serialization.Serializable

sealed class Screens {

    @Serializable
    data object Home

    @Serializable
    data class DriverAssignment(
        val driverName: String
    )
}
