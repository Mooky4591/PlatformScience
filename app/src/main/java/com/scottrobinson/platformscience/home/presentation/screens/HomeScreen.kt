package com.scottrobinson.platformscience.home.presentation.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.scottrobinson.platformscience.home.presentation.viewmodel.HomeState

@Composable
fun HomeScreen(state: HomeState) {

    LazyColumn {
        items(state.drivers.size) { index ->
            val driver = state.drivers[index]
            Text(driver.name)
        }
    }
}