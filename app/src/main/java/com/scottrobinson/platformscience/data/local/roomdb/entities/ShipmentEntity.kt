package com.scottrobinson.platformscience.data.local.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shipments")
data class ShipmentEntity(
    @PrimaryKey(autoGenerate = true)
    val shipmentId: Long = 0,
    val destination: String
)