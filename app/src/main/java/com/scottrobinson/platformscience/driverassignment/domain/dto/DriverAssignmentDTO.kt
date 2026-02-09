package com.scottrobinson.platformscience.driverassignment.domain.dto

data class DriverAssignmentDTO(
    val driverName: String,
    val shipmentDestination: String,
    val suitabilityScore: Double
)
