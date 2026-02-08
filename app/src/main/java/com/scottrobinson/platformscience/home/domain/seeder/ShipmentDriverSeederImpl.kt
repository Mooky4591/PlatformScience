package com.scottrobinson.platformscience.home.domain.seeder

import com.scottrobinson.platformscience.data.local.roomdb.daos.DriverDao
import com.scottrobinson.platformscience.data.local.roomdb.daos.ShipmentDao
import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity
import com.scottrobinson.platformscience.home.domain.seeder.`object`.SeedResult
import javax.inject.Inject


class RoomShipmentDriverSeeder @Inject constructor(
    private val driverDao: DriverDao,
    private val shipmentDao: ShipmentDao,
) : ShipmentDriverSeeder {

    override suspend fun seed(drivers: List<String>, shipments: List<String>): SeedResult {
        val driverEntities = drivers.map { DriverEntity(name = it) }
        val shipmentEntities = shipments.map { ShipmentEntity(destination = it) }

        driverDao.upsertAll(driverEntities)
        shipmentDao.upsertAll(shipmentEntities)

        // Fetch back entities with generated IDs
        val seededDrivers = driverDao.getAllDrivers()
        val seededShipments = shipmentDao.getAllShipments()

        return SeedResult(drivers = seededDrivers, shipments = seededShipments)
    }
}