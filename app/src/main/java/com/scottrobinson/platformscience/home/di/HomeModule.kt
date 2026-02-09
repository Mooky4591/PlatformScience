package com.scottrobinson.platformscience.home.di

import android.content.Context
import androidx.room.Room
import com.scottrobinson.platformscience.data.local.roomdb.daos.AssignmentDao
import com.scottrobinson.platformscience.data.local.roomdb.daos.DriverDao
import com.scottrobinson.platformscience.data.local.roomdb.daos.ShipmentDao
import com.scottrobinson.platformscience.data.local.roomdb.database.AppDatabase
import com.scottrobinson.platformscience.home.data.datasource.ResourceShipmentDriverDataSourceImpl
import com.scottrobinson.platformscience.home.data.datasource.ShipmentDriverDataSource
import com.scottrobinson.platformscience.home.domain.DriversListRepo
import com.scottrobinson.platformscience.home.domain.DriversListRepoImpl
import com.scottrobinson.platformscience.home.domain.assignment.Assignment
import com.scottrobinson.platformscience.home.domain.assignment.AssignmentImpl
import com.scottrobinson.platformscience.home.domain.parser.ShipmentDriverParser
import com.scottrobinson.platformscience.home.domain.parser.ShipmentDriverParserImpl
import com.scottrobinson.platformscience.home.domain.seeder.RoomShipmentDriverSeederImpl
import com.scottrobinson.platformscience.home.domain.seeder.ShipmentDriverSeeder
import com.scottrobinson.platformscience.home.domain.suitability.SuitabilityScorer
import com.scottrobinson.platformscience.home.domain.suitability.SuitabilityScorerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {

    @Binds
    @Singleton
    abstract fun bindDriversListRepo(
        driversListRepoImpl: DriversListRepoImpl
    ): DriversListRepo

    @Binds
    @Singleton
    abstract fun bindShipmentDriverParser(
        shipmentDriverParserImpl: ShipmentDriverParserImpl
    ): ShipmentDriverParser

    @Binds
    @Singleton
    abstract fun bindShipmentDriverSeeder(
        roomShipmentDriverSeederImpl: RoomShipmentDriverSeederImpl
    ): ShipmentDriverSeeder

    @Binds
    @Singleton
    abstract fun bindSuitabilityScorer(
        suitabilityScorerImpl: SuitabilityScorerImpl
    ): SuitabilityScorer

    @Binds
    @Singleton
    abstract fun bindAssignment(
        assignmentImpl: AssignmentImpl
    ): Assignment

    companion object {
        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app_database"
            )
            .fallbackToDestructiveMigration()
            .build()
        }

        @Provides
        fun provideDriverDao(appDatabase: AppDatabase): DriverDao {
            return appDatabase.driverDao()
        }

        @Provides
        fun provideShipmentDao(appDatabase: AppDatabase): ShipmentDao {
            return appDatabase.shipmentDao()
        }

        @Provides
        fun provideAssignmentDao(appDatabase: AppDatabase): AssignmentDao {
            return appDatabase.assignmentDao()
        }

        @Provides
        @Singleton
        fun provideShipmentDriverDataSource(@ApplicationContext context: Context): ShipmentDriverDataSource {
            return ResourceShipmentDriverDataSourceImpl(context)
        }
    }
}
