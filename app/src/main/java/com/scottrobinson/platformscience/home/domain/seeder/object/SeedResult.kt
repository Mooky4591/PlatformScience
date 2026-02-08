package com.scottrobinson.platformscience.home.domain.seeder.`object`

import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity

data class SeedResult(
    val drivers: List<DriverEntity>,
    val shipments: List<ShipmentEntity>,
)
