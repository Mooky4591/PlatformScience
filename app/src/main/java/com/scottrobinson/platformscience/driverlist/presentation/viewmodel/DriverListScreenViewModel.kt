package com.scottrobinson.platformscience.driverlist.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scottrobinson.platformscience.driverlist.domain.DriversListRepo
import com.scottrobinson.platformscience.driverlist.domain.dtos.DriverListDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val driversListRepo: DriversListRepo
) : ViewModel() {

    var state by mutableStateOf(DriverListState())
        private set

    init {
        viewModelScope.launch {
            runCatching { driversListRepo.getDriverList() }
                .onSuccess { drivers -> state = state.copy(drivers = drivers) }
                .onFailure { /* handle */ }
        }
    }
}

data class DriverListState(
    val drivers: List<DriverListDTO> = emptyList()
)
