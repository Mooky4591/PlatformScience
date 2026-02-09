package com.scottrobinson.platformscience.data.local.roomdb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scottrobinson.platformscience.data.local.roomdb.entities.AssignmentEntity

@Dao
interface AssignmentDao {

    @Query("SELECT * FROM assignments WHERE driverName = :driverName LIMIT 1")
    suspend fun getAssignment(driverName: String): AssignmentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(assignments: List<AssignmentEntity>)

    @Query("SELECT COUNT(*) FROM assignments")
    suspend fun count(): Int
}