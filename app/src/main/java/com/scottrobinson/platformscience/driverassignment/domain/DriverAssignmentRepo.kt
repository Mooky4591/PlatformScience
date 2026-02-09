package com.scottrobinson.platformscience.driverassignment.domain

import com.scottrobinson.platformscience.driverassignment.domain.dto.DriverAssignmentDTO

interface DriverAssignmentRepo {
    suspend fun getDriverAssignment(driverName: String): DriverAssignmentDTO
}