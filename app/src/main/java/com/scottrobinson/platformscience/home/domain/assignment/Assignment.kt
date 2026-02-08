package com.scottrobinson.platformscience.home.domain.assignment

import com.scottrobinson.platformscience.data.local.roomdb.entities.AssignmentEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity
import com.scottrobinson.platformscience.home.domain.suitability.SuitabilityScorer

interface Assignment {
    /**
     * Returns a 1-to-1 matching that maximizes total score.
     * Assumes single-day dataset (no historical carry).
     */
    fun assign(
        drivers: List<DriverEntity>,
        shipments: List<ShipmentEntity>,
        scorer: SuitabilityScorer
    ): List<AssignmentEntity>
}