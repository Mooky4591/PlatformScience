package com.scottrobinson.platformscience.driverlist.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scottrobinson.platformscience.driverlist.presentation.events.DriverListScreenEvents
import com.scottrobinson.platformscience.driverlist.presentation.viewmodel.DriverListState

@Composable
fun HomeScreen(
    state: DriverListState,
    onEvent: (DriverListScreenEvents) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp) // space between rows
    ) {
        item {
            Text(text = "Drivers")
        }
        items(
            items = state.drivers,
            key = { it.name }
        ) { driver ->
            DriverRow(
                name = driver.name,
                clickable = true,
                onClick = { onEvent(DriverListScreenEvents.OnDiverSelected(driver.name)) }
            )
        }
    }
}

@Composable
fun DriverRow(
    name: String,
    onClick: () -> Unit,
    clickable: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = clickable, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

