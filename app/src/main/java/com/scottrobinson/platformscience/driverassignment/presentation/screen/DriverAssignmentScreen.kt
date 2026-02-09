package com.scottrobinson.platformscience.driverassignment.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.scottrobinson.platformscience.driverassignment.presentation.viewmodel.DriverAssignmentState

@Composable
fun DriverAssignmentScreen(
    state: DriverAssignmentState,
) {

    Text(text = state.assignment?.shipmentDestination ?: "No Driver Name")

}