package com.scottrobinson.platformscience.data.local.roomdb.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drivers")
data class DriverEntity(
    @PrimaryKey
    val name: String
)
