package com.scottrobinson.platformscience.driverlist.domain

import com.scottrobinson.platformscience.data.local.roomdb.daos.AssignmentDao
import com.scottrobinson.platformscience.data.local.roomdb.entities.AssignmentEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity
import com.scottrobinson.platformscience.driverlist.data.datasource.ShipmentDriverDataSource
import com.scottrobinson.platformscience.driverlist.domain.assignment.Assignment
import com.scottrobinson.platformscience.driverlist.domain.dtos.Payload
import com.scottrobinson.platformscience.driverlist.domain.parser.ShipmentDriverParser
import com.scottrobinson.platformscience.driverlist.domain.seeder.ShipmentDriverSeeder
import com.scottrobinson.platformscience.driverlist.domain.seeder.`object`.SeedResult
import com.scottrobinson.platformscience.driverlist.domain.suitability.SuitabilityScorer
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class DriversListRepoImplTest {
    private lateinit var assignmentDao: AssignmentDao
    private lateinit var dataSource: ShipmentDriverDataSource
    private lateinit var parser: ShipmentDriverParser
    private lateinit var seeder: ShipmentDriverSeeder
    private lateinit var scorer: SuitabilityScorer
    private lateinit var optimizer: Assignment
    private lateinit var repo: DriversListRepoImpl

    @Before
    fun setUp() {
        assignmentDao = mock()
        dataSource = mock()
        parser = mock()
        seeder = mock()
        scorer = mock()
        optimizer = mock()
        repo = DriversListRepoImpl(assignmentDao, dataSource, parser, seeder, scorer, optimizer)
    }

    @Test
    fun `getDriverList returns correct driver names and persists assignments`() = runBlocking {
        val json = "{drivers: ['A'], shipments: ['B']}"
        val parsedDrivers = listOf("Alice", "Bob")
        val parsedShipments = listOf("NY", "LA")
        val driverEntities = parsedDrivers.map { DriverEntity(it) }
        val shipmentEntities = parsedShipments.map { ShipmentEntity(it) }
        val assignments = listOf(
            AssignmentEntity(driverName = "Alice", shipmentDestination = "NY", suitabilityScore = 1.0),
            AssignmentEntity(driverName = "Bob", shipmentDestination = "LA", suitabilityScore = 2.0)
        )
        whenever(dataSource.loadJson()).thenReturn(json)
        whenever(parser.parse(json)).thenReturn(Payload(parsedShipments, parsedDrivers))
        whenever(seeder.seed(parsedDrivers, parsedShipments)).thenReturn(SeedResult(driverEntities, shipmentEntities))
        whenever(optimizer.assign(driverEntities, shipmentEntities, scorer)).thenReturn(assignments)

        val result = repo.getDriverList()

        verify(assignmentDao).upsertAll(assignments)
        assertEquals(parsedDrivers, result.map { it.name })
    }

    @Test
    fun `getDriverList returns empty when no drivers`() = runBlocking {
        val json = "{}"
        whenever(dataSource.loadJson()).thenReturn(json)
        whenever(parser.parse(json)).thenReturn(Payload(emptyList(), emptyList()))
        whenever(seeder.seed(emptyList(), emptyList())).thenReturn(SeedResult(emptyList(), emptyList()))
        whenever(optimizer.assign(emptyList(), emptyList(), scorer)).thenReturn(emptyList())

        val result = repo.getDriverList()
        verify(assignmentDao).upsertAll(emptyList())
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getDriverList propagates exceptions`() = runBlocking {
        whenever(dataSource.loadJson()).thenThrow(RuntimeException("fail"))
        try {
            repo.getDriverList()
            fail("Exception not thrown")
        } catch (e: RuntimeException) {
            assertEquals("fail", e.message)
        }
    }
}
