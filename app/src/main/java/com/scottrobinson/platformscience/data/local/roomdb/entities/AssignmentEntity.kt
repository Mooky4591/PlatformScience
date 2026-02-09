package com.scottrobinson.platformscience.data.local.roomdb.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "assignments",
    /* creates a DB index on driverName and shipmentDestination so table cannot contain two rows with the same
    driverName or shipmentDestination so a driver can only be assigned to one shipment and a shipment can only
    be assigned to one driver */
    indices = [
        Index(value = ["driverName"], unique = true),
        Index(value = ["shipmentDestination"], unique = true)
    ],
    //Every assignments.driverName must match an existing drivers.name
    foreignKeys = [
        ForeignKey(
            entity = DriverEntity::class,
            parentColumns = ["name"],
            childColumns = ["driverName"],
            onDelete = ForeignKey.CASCADE
        ),
        //Every assignments.shipmentDestination must match an existing drivers.destination
        ForeignKey(
            entity = ShipmentEntity::class,
            parentColumns = ["destination"],
            childColumns = ["shipmentDestination"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AssignmentEntity(
    @PrimaryKey
    val driverName: String,
    val shipmentDestination: String,
    val suitabilityScore: Double
)
