package com.scottrobinson.platformscience.driverlist.domain.parser

import com.scottrobinson.platformscience.driverlist.domain.dtos.Payload

interface ShipmentDriverParser {
    fun parse(jsonText: String): Payload
}