package com.scottrobinson.platformscience.driverassignment.domain

import com.scottrobinson.platformscience.data.local.roomdb.daos.AssignmentDao
import com.scottrobinson.platformscience.data.local.roomdb.entities.AssignmentEntity
import com.scottrobinson.platformscience.driverassignment.domain.dto.DriverAssignmentDTO

private fun AssignmentEntity?.toDriverAssignmentDTO(): DriverAssignmentDTO {
    return DriverAssignmentDTO(
        driverName = this?.driverName ?: "",
        shipmentDestination = this?.shipmentDestination ?: "",
        suitabilityScore = this?.suitabilityScore ?: 0.0
    )
}

class DriverAssignmentRepoImpl(
    private val assignmentDao: AssignmentDao
) : DriverAssignmentRepo {
    override suspend fun getDriverAssignment(driverName: String): DriverAssignmentDTO {
        return assignmentDao.getAssignment(driverName)?.toDriverAssignmentDTO()
            ?: DriverAssignmentDTO(
                driverName = "",
                shipmentDestination = "",
                suitabilityScore = 0.0
            )
    }
}