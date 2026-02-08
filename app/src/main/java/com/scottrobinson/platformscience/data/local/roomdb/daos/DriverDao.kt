package com.scottrobinson.platformscience.data.local.roomdb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity

@Dao
interface DriverDao {

    @Query("SELECT * FROM drivers ORDER BY name")
    fun observeDrivers(): kotlinx.coroutines.flow.Flow<List<DriverEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(drivers: List<DriverEntity>)
}
