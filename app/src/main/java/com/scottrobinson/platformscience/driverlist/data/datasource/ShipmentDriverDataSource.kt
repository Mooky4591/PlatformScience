package com.scottrobinson.platformscience.driverlist.data.datasource

interface ShipmentDriverDataSource {
    suspend fun loadJson(): String
}