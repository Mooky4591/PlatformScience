package com.scottrobinson.platformscience.home.domain.dtos

data class Payload(
    val shipments: List<String>,
    val drivers: List<String>
)
