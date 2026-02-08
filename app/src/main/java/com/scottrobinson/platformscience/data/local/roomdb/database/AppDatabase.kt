package com.scottrobinson.platformscience.data.local.roomdb.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scottrobinson.platformscience.data.local.roomdb.daos.AssignmentDao
import com.scottrobinson.platformscience.data.local.roomdb.daos.DriverDao
import com.scottrobinson.platformscience.data.local.roomdb.daos.ShipmentDao
import com.scottrobinson.platformscience.data.local.roomdb.entities.AssignmentEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.DriverEntity
import com.scottrobinson.platformscience.data.local.roomdb.entities.ShipmentEntity

@Database(
    entities = [DriverEntity::class, ShipmentEntity::class, AssignmentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun shipmentDao(): ShipmentDao
    abstract fun assignmentDao(): AssignmentDao

}