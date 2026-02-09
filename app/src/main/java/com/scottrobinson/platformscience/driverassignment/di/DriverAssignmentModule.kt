package com.scottrobinson.platformscience.driverassignment.di

import com.scottrobinson.platformscience.driverassignment.domain.DriverAssignmentRepo
import com.scottrobinson.platformscience.driverassignment.domain.DriverAssignmentRepoImpl
import com.scottrobinson.platformscience.data.local.roomdb.daos.AssignmentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DriverAssignmentModule {
    @Provides
    @Singleton
    fun provideDriverAssignmentRepo(assignmentDao: AssignmentDao): DriverAssignmentRepo {
        return DriverAssignmentRepoImpl(assignmentDao)
    }
}