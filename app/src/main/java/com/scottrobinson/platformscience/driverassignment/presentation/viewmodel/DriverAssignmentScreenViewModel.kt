package com.scottrobinson.platformscience.driverassignment.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scottrobinson.platformscience.driverassignment.domain.DriverAssignmentRepo
import com.scottrobinson.platformscience.driverassignment.domain.dto.DriverAssignmentDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriverAssignmentScreenViewModel @Inject constructor(
    private val driverAssignmentRepo: DriverAssignmentRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val driverName: String = savedStateHandle["driverName"] ?: ""

    var state = DriverAssignmentState(driverName = driverName)
        private set

    init {
        load(state.driverName.orEmpty())
    }

    private fun load(driverName: String) {
        viewModelScope.launch {
            val assignment = driverAssignmentRepo.getDriverAssignment(driverName)
            state = state.copy(assignment = assignment)
        }
    }

}

data class DriverAssignmentState(
    val driverName: String? = null,
    val assignment: DriverAssignmentDTO? = null
)