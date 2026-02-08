package com.scottrobinson.platformscience.data.local.roomdb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scottrobinson.platformscience.data.local.roomdb.entities.AssignmentEntity

@Dao
interface AssignmentDao {

    @Query("SELECT * FROM assignments WHERE driverId = :driverId LIMIT 1")
    suspend fun getAssignment(driverId: String): AssignmentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(assignments: List<AssignmentEntity>)

    @Query("SELECT COUNT(*) FROM assignments")
    suspend fun count(): Int
}