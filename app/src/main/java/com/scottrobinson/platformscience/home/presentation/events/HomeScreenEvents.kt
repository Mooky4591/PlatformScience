package com.scottrobinson.platformscience.home.presentation.events

sealed interface HomeScreenEvents {

    data class OnDiverSelected(val name: String) : HomeScreenEvents

}
