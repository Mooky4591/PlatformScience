package com.scottrobinson.platformscience.driverlist.presentation.events

sealed interface DriverListScreenEvents {

    data class OnDiverSelected(val name: String) : DriverListScreenEvents

}
