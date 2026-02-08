package com.scottrobinson.platformscience.data.local.roomdb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity

@Dao
interface ShipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(shipments: List<ShipmentEntity>)

    @Query("SELECT * FROM shipments ORDER BY destination")
    suspend fun getAllShipments(): List<ShipmentEntity>

    @Query("SELECT COUNT(*) FROM shipments")
    suspend fun count(): Int
}