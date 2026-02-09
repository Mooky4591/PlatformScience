package com.scottrobinson.platformscience.driverlist.domain.seeder

import com.scottrobinson.platformscience.data.local.roomdb.daos.DriverDao
import com.scottrobinson.platformscience.data.local.roomdb.daos.ShipmentDao
import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity
import com.scottrobinson.platformscience.driverlist.domain.seeder.`object`.SeedResult
import javax.inject.Inject

class RoomShipmentDriverSeederImpl @Inject constructor(
    private val driverDao: DriverDao,
    private val shipmentDao: ShipmentDao,
) : ShipmentDriverSeeder {

    override suspend fun seed(drivers: List<String>, shipments: List<String>): SeedResult {
        val driverEntities = drivers.map { DriverEntity(name = it) }
        val shipmentEntities = shipments.map { ShipmentEntity(destination = it) }

        driverDao.upsertAll(driverEntities)
        shipmentDao.upsertAll(shipmentEntities)

        // Fetch back entities with generated IDs (autogen PKs)
        val seededDrivers: List<DriverEntity> = driverDao.getAllDriversList()
        val seededShipments: List<ShipmentEntity> = shipmentDao.getAllShipmentsList()

        return SeedResult(drivers = seededDrivers, shipments = seededShipments)
    }
}
