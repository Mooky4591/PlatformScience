package com.scottrobinson.platformscience.home.domain.parser

import com.scottrobinson.platformscience.home.domain.dtos.Payload

interface ShipmentDriverParser {
    fun parse(jsonText: String): Payload
}