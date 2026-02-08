package com.scottrobinson.platformscience.home.domain.suitability

interface SuitabilityScorer {
    fun score(driverName: String, shipmentDestination: String): Double
}