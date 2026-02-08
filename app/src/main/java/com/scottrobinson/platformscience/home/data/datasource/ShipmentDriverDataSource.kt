package com.scottrobinson.platformscience.home.data.datasource

interface ShipmentDriverDataSource {
    suspend fun loadJson(): String
}