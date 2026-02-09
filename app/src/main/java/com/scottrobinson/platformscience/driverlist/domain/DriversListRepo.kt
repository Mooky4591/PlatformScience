package com.scottrobinson.platformscience.driverlist.domain

import com.scottrobinson.platformscience.driverlist.domain.dtos.DriverListDTO

interface DriversListRepo {

    suspend fun getDriverList() : List<DriverListDTO>
}