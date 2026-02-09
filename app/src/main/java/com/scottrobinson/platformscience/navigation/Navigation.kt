package com.scottrobinson.platformscience.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.scottrobinson.platformscience.driverlist.presentation.screens.DriverListScreen
import com.scottrobinson.platformscience.driverlist.presentation.viewmodel.HomeScreenViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.scottrobinson.platformscience.driverassignment.presentation.screen.DriverAssignmentScreen
import com.scottrobinson.platformscience.driverassignment.presentation.viewmodel.DriverAssignmentScreenViewModel
import com.scottrobinson.platformscience.driverlist.presentation.events.DriverListScreenEvents


@Composable
fun Nav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Home) {

        composable<Screens.Home>{
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            val state = homeViewModel.state
            DriverListScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is DriverListScreenEvents.OnDiverSelected ->
                            navController.navigate(Screens.DriverAssignment(event.name))
                    }
                }
            )
        }

        composable<Screens.DriverAssignment>{backstackEntry ->
            val driverViewModel = hiltViewModel<DriverAssignmentScreenViewModel>(backstackEntry)
            val state = driverViewModel.state
            DriverAssignmentScreen(state = state)

        }
    }
}