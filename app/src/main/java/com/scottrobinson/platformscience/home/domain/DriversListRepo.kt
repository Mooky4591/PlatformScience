package com.scottrobinson.platformscience.home.domain

import com.scottrobinson.platformscience.home.domain.dtos.DriverListDTO

interface DriversListRepo {

    suspend fun getDriverList() : List<DriverListDTO>
}