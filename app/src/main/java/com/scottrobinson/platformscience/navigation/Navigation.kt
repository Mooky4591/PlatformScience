package com.scottrobinson.platformscience.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Nav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Home) {
        composable<Screens.Home>{

        }
    }
}