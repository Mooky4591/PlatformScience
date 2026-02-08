package com.scottrobinson.platformscience.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.scottrobinson.platformscience.home.presentation.screens.HomeScreen
import com.scottrobinson.platformscience.home.presentation.viewmodel.HomeScreenViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.scottrobinson.platformscience.home.presentation.events.HomeScreenEvents


@Composable
fun Nav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Home) {

        composable<Screens.Home>{
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            val state = homeViewModel.state
            ObserveAsEvents(homeViewModel.event) { event ->
                when (event) {
                    is HomeScreenEvents.OnDiverSelected ->
                        navController.navigate(Screens.DriveDetails)
                }
            }
            HomeScreen(
                state
            )
        }

        composable<Screens.DriveDetails>{

        }
    }
}