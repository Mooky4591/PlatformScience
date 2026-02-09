package com.scottrobinson.platformscience.driverlist.domain.seeder

import com.scottrobinson.platformscience.data.local.roomdb.daos.DriverDao
import com.scottrobinson.platformscience.data.local.roomdb.daos.ShipmentDao
import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class RoomShipmentDriverSeederImplTest {
    private lateinit var driverDao: DriverDao
    private lateinit var shipmentDao: ShipmentDao
    private lateinit var seeder: RoomShipmentDriverSeederImpl

    @Before
    fun setUp() {
        driverDao = mock()
        shipmentDao = mock()
        seeder = RoomShipmentDriverSeederImpl(driverDao, shipmentDao)
    }

    @Test
    fun `seed inserts drivers and shipments and returns SeedResult`() = runBlocking {
        val drivers = listOf("Alice", "Bob")
        val shipments = listOf("NY", "LA")
        val driverEntities = drivers.map { DriverEntity(it) }
        val shipmentEntities = shipments.map { ShipmentEntity(it) }
        whenever(driverDao.getAllDriversList()).thenReturn(driverEntities)
        whenever(shipmentDao.getAllShipmentsList()).thenReturn(shipmentEntities)

        val result = seeder.seed(drivers, shipments)

        verify(driverDao).upsertAll(driverEntities)
        verify(shipmentDao).upsertAll(shipmentEntities)
        assertEquals(driverEntities, result.drivers)
        assertEquals(shipmentEntities, result.shipments)
    }

    @Test
    fun `seed with empty lists returns empty SeedResult`() = runBlocking {
        whenever(driverDao.getAllDriversList()).thenReturn(emptyList())
        whenever(shipmentDao.getAllShipmentsList()).thenReturn(emptyList())
        val result = seeder.seed(emptyList(), emptyList())
        verify(driverDao).upsertAll(emptyList())
        verify(shipmentDao).upsertAll(emptyList())
        assertTrue(result.drivers.isEmpty())
        assertTrue(result.shipments.isEmpty())
    }

    @Test
    fun `seed handles duplicate names`() = runBlocking {
        val drivers = listOf("Alice", "Alice")
        val shipments = listOf("NY", "NY")
        val driverEntities = drivers.map { DriverEntity(it) }
        val shipmentEntities = shipments.map { ShipmentEntity(it) }
        whenever(driverDao.getAllDriversList()).thenReturn(driverEntities)
        whenever(shipmentDao.getAllShipmentsList()).thenReturn(shipmentEntities)
        val result = seeder.seed(drivers, shipments)
        verify(driverDao).upsertAll(driverEntities)
        verify(shipmentDao).upsertAll(shipmentEntities)
        assertEquals(driverEntities, result.drivers)
        assertEquals(shipmentEntities, result.shipments)
    }
}
