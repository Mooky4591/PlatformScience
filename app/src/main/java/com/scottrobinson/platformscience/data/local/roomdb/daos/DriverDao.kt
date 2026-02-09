package com.scottrobinson.platformscience.data.local.roomdb.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity

@Dao
interface DriverDao {

    @Query("SELECT * FROM drivers ORDER BY name")
    fun observeDrivers(): kotlinx.coroutines.flow.Flow<List<DriverEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(drivers: List<DriverEntity>)

    @Query("SELECT * FROM drivers ORDER BY name")
    fun pagingSource(): PagingSource<Int, DriverEntity>

    @Query("SELECT * FROM drivers ORDER BY name")
    suspend fun getAllDriversList(): List<DriverEntity>

    @Query("SELECT COUNT(*) FROM drivers")
    suspend fun count(): Int

}
