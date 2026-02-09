package com.scottrobinson.platformscience.driverassignment.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.scottrobinson.platformscience.driverassignment.presentation.viewmodel.DriverAssignmentState

@Composable
fun DriverAssignmentScreen(
    state: DriverAssignmentState,
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = state.assignment?.shipmentDestination ?: "No Driver Name",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}