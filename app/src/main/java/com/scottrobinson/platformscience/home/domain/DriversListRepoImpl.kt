package com.scottrobinson.platformscience.home.domain

import com.scottrobinson.platformscience.data.local.roomdb.daos.AssignmentDao
import com.scottrobinson.platformscience.home.data.datasource.ShipmentDriverDataSource
import com.scottrobinson.platformscience.home.domain.assignment.Assignment
import com.scottrobinson.platformscience.home.domain.dtos.DriverListDTO
import com.scottrobinson.platformscience.home.domain.parser.ShipmentDriverParser
import com.scottrobinson.platformscience.home.domain.seeder.ShipmentDriverSeeder
import com.scottrobinson.platformscience.home.domain.suitability.SuitabilityScorer
import javax.inject.Inject

/**
 * Repo responsibilities:
 * - orchestrate: load -> parse -> persist -> assign -> return drivers
 * - no parsing rules, no scoring rules, no assignment algorithm inside this class
 */
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
