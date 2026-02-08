package com.scottrobinson.platformscience.home.data.datasource

import android.content.Context
import androidx.annotation.RawRes
import com.scottrobinson.platformscience.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceShipmentDriverDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @RawRes private val resId: Int = R.raw.shipments
) : ShipmentDriverDataSource {
    override suspend fun loadJson(): String {
        return context.resources
            .openRawResource(resId)
            .bufferedReader()
            .use { it.readText() }
    }
}