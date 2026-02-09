package com.scottrobinson.platformscience.data.local.roomdb.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShipmentDao {

    @Query("SELECT * FROM shipments ORDER BY destination")
    fun observeShipments(): Flow<List<ShipmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(shipments: List<ShipmentEntity>)

    @Query("SELECT * FROM shipments ORDER BY destination")
    fun pagingSource(): PagingSource<Int, ShipmentEntity>

    @Query("SELECT * FROM shipments ORDER BY destination")
    suspend fun getAllShipmentsList(): List<ShipmentEntity>

    @Query("SELECT COUNT(*) FROM shipments")
    suspend fun count(): Int
}
