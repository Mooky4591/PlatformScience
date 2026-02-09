package com.scottrobinson.platformscience.home.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scottrobinson.platformscience.home.domain.DriversListRepo
import com.scottrobinson.platformscience.home.domain.dtos.DriverListDTO
import com.scottrobinson.platformscience.home.presentation.events.HomeScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val driversListRepo: DriversListRepo
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private val _events = MutableSharedFlow<HomeScreenEvents>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            runCatching { driversListRepo.getDriverList() }
                .onSuccess { drivers -> state = state.copy(drivers = drivers) }
                .onFailure { /* handle */ }
        }
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.OnDiverSelected -> {
                // one-shot event for navigation layer
                _events.tryEmit(event)
            }
        }
    }
}

data class HomeState(
    val drivers: List<DriverListDTO> = emptyList()
)
