package com.scottrobinson.platformscience.driverlist.domain

import com.scottrobinson.platformscience.data.local.roomdb.daos.AssignmentDao
import com.scottrobinson.platformscience.driverlist.data.datasource.ShipmentDriverDataSource
import com.scottrobinson.platformscience.driverlist.domain.assignment.Assignment
import com.scottrobinson.platformscience.driverlist.domain.parser.ShipmentDriverParser
import com.scottrobinson.platformscience.driverlist.domain.seeder.ShipmentDriverSeeder
import com.scottrobinson.platformscience.driverlist.domain.suitability.SuitabilityScorer
import com.scottrobinson.platformscience.driverlist.domain.dtos.DriverListDTO
import javax.inject.Inject

class DriversListRepoImpl @Inject constructor(
    private val assignmentDao: AssignmentDao,
    private val dataSource: ShipmentDriverDataSource,
    private val parser: ShipmentDriverParser,
    private val seeder: ShipmentDriverSeeder,
    private val scorer: SuitabilityScorer,
    private val optimizer: Assignment,
) : DriversListRepo {

    override suspend fun getDriverList(): List<DriverListDTO> {
        val jsonText = dataSource.loadJson()
        val parsed = parser.parse(jsonText)

        val seeded = seeder.seed(
            drivers = parsed.drivers,
            shipments = parsed.shipments
        )

        val assignments = optimizer.assign(
            drivers = seeded.drivers,
            shipments = seeded.shipments,
            scorer = scorer
        )

        // Persist assignments (1 shipment <-> 1 driver enforced by DB unique indexes)
        assignmentDao.upsertAll(assignments)

        return seeded.drivers.map { DriverListDTO(name = it.name) }
    }
}
