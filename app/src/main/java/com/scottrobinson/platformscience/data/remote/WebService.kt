package com.scottrobinson.platformscience.data.remote

import com.scottrobinson.platformscience.R

interface WebService {

    suspend fun getInfo(): String {
        return R.raw.shipments.toString()
    }
}