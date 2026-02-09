package com.scottrobinson.platformscience.driverassignment.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scottrobinson.platformscience.driverassignment.presentation.viewmodel.DriverAssignmentState
import com.scottrobinson.platformscience.home.presentation.screens.DriverRow
@Composable
fun DriverAssignmentScreen(state: DriverAssignmentState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Shipment Destination")

        val destination = state.assignment?.shipmentDestination
        if (destination == null) {
            Text(text = "Loading...")
            return@Column
        }

        DriverRow(
            name = destination,
            onClick = {},
            clickable = false
        )
    }
}
