package com.scottrobinson.platformscience.driverlist.domain.assignment

import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity
import com.scottrobinson.platformscience.driverlist.domain.suitability.SuitabilityScorer
import org.junit.Assert
import org.junit.Test

class AssignmentImplTest {
    private class TestScorer : SuitabilityScorer {
        override fun score(driverName: String, shipmentDestination: String): Double {
            // Simple deterministic score for testing
            return (driverName.length + shipmentDestination.length).toDouble()
        }
    }

    private val scorer = TestScorer()
    private val assignment = AssignmentImpl()

    @Test
    fun `assign returns empty when no drivers`() {
        val result = assignment.assign(emptyList(), listOf(ShipmentEntity("A")), scorer)
        Assert.assertTrue(result.isEmpty())
    }

    @Test
    fun `assign returns empty when no shipments`() {
        val result = assignment.assign(listOf(DriverEntity("A")), emptyList(), scorer)
        Assert.assertTrue(result.isEmpty())
    }

    @Test
    fun `assign matches drivers and shipments one to one`() {
        val drivers = listOf(DriverEntity("Alice"), DriverEntity("Bob"))
        val shipments = listOf(ShipmentEntity("NY"), ShipmentEntity("LA"))
        val result = assignment.assign(drivers, shipments, scorer)
        Assert.assertEquals(2, result.size)
        val driverNames = result.map { it.driverName }
        val shipmentNames = result.map { it.shipmentDestination }
        Assert.assertTrue(driverNames.containsAll(listOf("Alice", "Bob")))
        Assert.assertTrue(shipmentNames.containsAll(listOf("NY", "LA")))
    }

    @Test
    fun `assign handles more drivers than shipments`() {
        val drivers = listOf(DriverEntity("A"), DriverEntity("B"), DriverEntity("C"))
        val shipments = listOf(ShipmentEntity("X"))
        val result = assignment.assign(drivers, shipments, scorer)
        // Only one real assignment
        Assert.assertEquals(1, result.size)
        Assert.assertTrue(result[0].shipmentDestination == "X")
    }

    @Test
    fun `assign handles more shipments than drivers`() {
        val drivers = listOf(DriverEntity("A"))
        val shipments = listOf(ShipmentEntity("X"), ShipmentEntity("Y"), ShipmentEntity("Z"))
        val result = assignment.assign(drivers, shipments, scorer)
        // Only one real assignment
        Assert.assertEquals(1, result.size)
        Assert.assertTrue(result[0].driverName == "A")
    }

    @Test
    fun `assign assigns correct suitability score`() {
        val drivers = listOf(DriverEntity("Ann"))
        val shipments = listOf(ShipmentEntity("TX"))
        val result = assignment.assign(drivers, shipments, scorer)
        Assert.assertEquals(1, result.size)
        val expectedScore = scorer.score("Ann", "TX")
        Assert.assertEquals(expectedScore, result[0].suitabilityScore, 0.0001)
    }
}