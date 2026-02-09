package com.scottrobinson.platformscience.driverlist.domain.dtos

data class Payload(
    val shipments: List<String>,
    val drivers: List<String>
)
