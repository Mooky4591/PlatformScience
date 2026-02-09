package com.scottrobinson.platformscience.driverassignment.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scottrobinson.platformscience.driverassignment.domain.DriverAssignmentRepo
import com.scottrobinson.platformscience.driverassignment.domain.dto.DriverAssignmentDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverAssignmentScreenViewModel @Inject constructor(
    private val driverAssignmentRepo: DriverAssignmentRepo
) : ViewModel() {

    var state by mutableStateOf(DriverAssignmentState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                assignment = driverAssignmentRepo.getDriverAssignment(
                    state.driverName ?: ""
                )
            )
        }
    }

    fun setDriverName(driverName: String) {
        state = state.copy(driverName = driverName)
    }

}

data class DriverAssignmentState(
    val driverName: String? = null,
    val assignment: DriverAssignmentDTO? = null
)