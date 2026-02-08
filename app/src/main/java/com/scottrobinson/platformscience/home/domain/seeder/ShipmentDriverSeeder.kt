package com.scottrobinson.platformscience.home.domain.seeder

import com.scottrobinson.platformscience.home.domain.seeder.`object`.SeedResult

interface ShipmentDriverSeeder {
    suspend fun seed(drivers: List<String>, shipments: List<String>): SeedResult
}