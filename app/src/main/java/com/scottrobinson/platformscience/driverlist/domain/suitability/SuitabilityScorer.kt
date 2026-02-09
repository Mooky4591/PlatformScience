package com.scottrobinson.platformscience.driverlist.domain.suitability

interface SuitabilityScorer {
    fun score(driverName: String, shipmentDestination: String): Double
}