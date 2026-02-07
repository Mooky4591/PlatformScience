package com.scottrobinson.platformscience.navigation

import kotlinx.serialization.Serializable

sealed class Screens {

    @Serializable
    data object Home
}
