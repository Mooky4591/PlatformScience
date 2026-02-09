package com.scottrobinson.platformscience.home.domain.assignment

import com.scottrobinson.platformscience.data.local.roomdb.entities.AssignmentEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity
import com.scottrobinson.platformscience.home.domain.suitability.SuitabilityScorer
import javax.inject.Inject
import kotlin.math.max


class AssignmentImpl @Inject constructor() : Assignment {

    override fun assign(
        drivers: List<DriverEntity>,
        shipments: List<ShipmentEntity>,
        scorer: SuitabilityScorer
    ): List<AssignmentEntity> {
        val n = drivers.size
        val m = shipments.size
        if (n == 0 || m == 0) return emptyList()

        val rows = n
        //If shipments < drivers, create dummy columns (fake shipments with score 0)
        // so every driver can be “matched” to something
        val cols = max(n, m)

        // Precompute scores for all real pairs and creates score[i][j] matrix
        //tracks the highest suitability score in the matrix
        var maxScore = 0.0
        val score = Array(rows) { DoubleArray(cols) }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                //only calculate scores of real shipments
                val s = if (j < m) scorer.score(drivers[i].name, shipments[j].destination) else 0.0
                score[i][j] = s
                if (s > maxScore) maxScore = s
            }
        }

        // Convert our "maximize suitability" problem into a "minimize cost" problem
        // by subtracting each score from the maximum score.
        // Hungarian algorithm works on minimization.
        val costMatrix = Array(rows + 1) { DoubleArray(cols + 1) }

        // costMatrix is 1-indexed to match the algorithm
        for (driverIndex in 1..rows) {
            for (shipmentIndex in 1..cols) {
                costMatrix[driverIndex][shipmentIndex] =
                    maxScore - score[driverIndex - 1][shipmentIndex - 1]
            }
        }

        // These arrays store the current state of the algorithm

        // Potential values for each driver (row)
        val driverPotential = DoubleArray(rows + 1)

        // Potential values for each shipment (column)
        val shipmentPotential = DoubleArray(cols + 1)

        // shipmentToDriver[j] = i -> means shipment j is assigned to driver i
        val shipmentToDriver = IntArray(cols + 1)

        // Used to reconstruct the augmenting path
        val previousShipment = IntArray(cols + 1)

        // Main loop: try to assign each driver
        for (driver in 1..rows) {

            // Start a new augmenting path from this driver
            shipmentToDriver[0] = driver
            var currentShipment = 0

            val minReducedCost = DoubleArray(cols + 1) { Double.POSITIVE_INFINITY }
            val visitedShipments = BooleanArray(cols + 1)

            // Find an augmenting path
            do {
                visitedShipments[currentShipment] = true
                val currentDriver = shipmentToDriver[currentShipment]

                var bestDelta = Double.POSITIVE_INFINITY
                var nextShipment = 0

                // Explore all shipments
                for (shipment in 1..cols) {
                    if (visitedShipments[shipment]) continue

                    // Reduced cost accounts for potentials
                    val reducedCost =
                        costMatrix[currentDriver][shipment] -
                                driverPotential[currentDriver] -
                                shipmentPotential[shipment]

                    if (reducedCost < minReducedCost[shipment]) {
                        minReducedCost[shipment] = reducedCost
                        previousShipment[shipment] = currentShipment
                    }

                    if (minReducedCost[shipment] < bestDelta) {
                        bestDelta = minReducedCost[shipment]
                        nextShipment = shipment
                    }
                }

                // Update potentials so at least one reduced cost becomes zero
                for (shipment in 0..cols) {
                    if (visitedShipments[shipment]) {
                        driverPotential[shipmentToDriver[shipment]] += bestDelta
                        shipmentPotential[shipment] -= bestDelta
                    } else {
                        minReducedCost[shipment] -= bestDelta
                    }
                }

                currentShipment = nextShipment

            } while (shipmentToDriver[currentShipment] != 0)

            // Reconstruct the assignment path
            do {
                val prev = previousShipment[currentShipment]
                shipmentToDriver[currentShipment] = shipmentToDriver[prev]
                currentShipment = prev
            } while (currentShipment != 0)
        }

        // p[j] = matched row for column j. Build row->col mapping.
        val rowToCol = IntArray(rows + 1)
        for (j in 1..cols) {
            val i = shipmentToDriver[j]
            if (i in 1..rows) rowToCol[i] = j
        }

        // Emit assignments only for real shipment columns (j <= m)
        val out = ArrayList<AssignmentEntity>(minOf(n, m))
        for (i in 1..rows) {
            val j = rowToCol[i]
            if (j in 1..m) {
                val driver = drivers[i - 1]
                val shipment = shipments[j - 1]
                val ss = score[i - 1][j - 1]
                out += AssignmentEntity(
                    driverName = driver.name,
                    shipmentDestination = shipment.destination,
                    suitabilityScore = ss
                )
            }
        }
        return out
    }
}