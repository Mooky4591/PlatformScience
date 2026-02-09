package com.scottrobinson.platformscience.driverlist.domain.seeder

import com.scottrobinson.platformscience.driverlist.domain.seeder.`object`.SeedResult

interface ShipmentDriverSeeder {
    suspend fun seed(drivers: List<String>, shipments: List<String>): SeedResult
}