package com.scottrobinson.platformscience.driverlist.presentation.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.scottrobinson.platformscience.driverlist.presentation.events.DriverListScreenEvents
import com.scottrobinson.platformscience.driverlist.presentation.viewmodel.DriverListState
import com.scottrobinson.platformscience.driverlist.domain.dtos.DriverListDTO
import org.junit.Rule
import org.junit.Test

class DriverListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysDrivers_andHeader_andHandlesClick() {
        val drivers = listOf(
            DriverListDTO("Alice"),
            DriverListDTO("Bob")
        )
        val state = DriverListState(drivers = drivers)
        var clickedName: String? = null
        composeTestRule.setContent {
            DriverListScreen(state = state, onEvent = {
                if (it is DriverListScreenEvents.OnDiverSelected) clickedName = it.name
            })
        }
        composeTestRule.onNodeWithText("Drivers").assertExists()
        composeTestRule.onNodeWithText("Alice").assertExists()
        composeTestRule.onNodeWithText("Bob").assertExists()
        composeTestRule.onNodeWithText("Alice").performClick()
        assert(clickedName == "Alice")
    }

    @Test
    fun driverRow_displaysName_andHandlesClick() {
        var clicked = false
        composeTestRule.setContent {
            DriverRow(name = "Charlie", clickable = true, onClick = { clicked = true })
        }
        composeTestRule.onNodeWithText("Charlie").assertExists()
        composeTestRule.onNodeWithText("Charlie").performClick()
        assert(clicked)
    }

    @Test
    fun driverRow_doesNotCallOnClick_whenNotClickable() {
        var clicked = false
        composeTestRule.setContent {
            DriverRow(name = "Dana", clickable = false, onClick = { clicked = true })
        }
        composeTestRule.onNodeWithText("Dana").assertExists()
        composeTestRule.onNodeWithText("Dana").performClick()
        assert(!clicked)
    }
}
