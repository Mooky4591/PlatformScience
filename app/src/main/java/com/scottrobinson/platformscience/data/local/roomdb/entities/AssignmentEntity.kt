package com.scottrobinson.platformscience.data.local.roomdb.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "assignments",
    /* creates a DB index on driverId and shipmentId so table cannot contain two rows with the same
    driverId or shipmentId so a driver can only be assigned to one shipment and a shipment can only
    be assigned to one driver */
    indices = [
        Index(value = ["driverId"], unique = true),
        Index(value = ["shipmentId"], unique = true)
    ],
    //Every assignments.driverId must match an existing drivers.driverId
    foreignKeys = [
        ForeignKey(
            entity = DriverEntity::class,
            parentColumns = ["driverId"],
            childColumns = ["driverId"],
            onDelete = ForeignKey.CASCADE
        ),
        //Every assignments.shipmentId must match an existing drivers.shipmentId
        ForeignKey(
            entity = ShipmentEntity::class,
            parentColumns = ["shipmentId"],
            childColumns = ["shipmentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AssignmentEntity(
    @PrimaryKey
    val driverId: Long,
    val shipmentId: Long,
    val suitabilityScore: Double
)

