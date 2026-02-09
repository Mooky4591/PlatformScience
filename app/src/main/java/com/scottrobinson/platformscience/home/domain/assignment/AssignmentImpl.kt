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

        //rows of the matrix are drivers
        val rows = n
        //If shipments < drivers, you create dummy columns (fake shipments with score 0)
        // so every driver can be “matched” to something
        val cols = max(n, m)

        // Precompute scores for all real pairs and creates score[i][j] matrix
        var maxScore = 0.0
        val score = Array(rows) { DoubleArray(cols) }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val s = if (j < m) scorer.score(drivers[i].name, shipments[j].destination) else 0.0
                score[i][j] = s
                if (s > maxScore) maxScore = s
            }
        }

        // Min-cost matrix
        val a = Array(rows + 1) { DoubleArray(cols + 1) }
        for (i in 1..rows) {
            for (j in 1..cols) {
                a[i][j] = maxScore - score[i - 1][j - 1]
            }
        }

        // Hungarian (minimization) for rectangular matrix (rows <= cols)
        val u = DoubleArray(rows + 1)
        val v = DoubleArray(cols + 1)
        val p = IntArray(cols + 1)     // matching for columns: row assigned to column j
        val way = IntArray(cols + 1)

        for (i in 1..rows) {
            p[0] = i
            var j0 = 0
            val minv = DoubleArray(cols + 1) { Double.POSITIVE_INFINITY }
            val used = BooleanArray(cols + 1)

            do {
                used[j0] = true
                val i0 = p[j0]
                var delta = Double.POSITIVE_INFINITY
                var j1 = 0
                for (j in 1..cols) {
                    if (used[j]) continue
                    val cur = a[i0][j] - u[i0] - v[j]
                    if (cur < minv[j]) {
                        minv[j] = cur
                        way[j] = j0
                    }
                    if (minv[j] < delta) {
                        delta = minv[j]
                        j1 = j
                    }
                }
                for (j in 0..cols) {
                    if (used[j]) {
                        u[p[j]] += delta
                        v[j] -= delta
                    } else {
                        minv[j] -= delta
                    }
                }
                j0 = j1
            } while (p[j0] != 0)

            do {
                val j1 = way[j0]
                p[j0] = p[j1]
                j0 = j1
            } while (j0 != 0)
        }

        // p[j] = matched row for column j. Build row->col mapping.
        val rowToCol = IntArray(rows + 1)
        for (j in 1..cols) {
            val i = p[j]
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