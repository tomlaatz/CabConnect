package com.acme.cabconnect.presentation.fahrten

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.acme.cabconnect.presentation.fahrten.components.Heading
import com.acme.cabconnect.ui.theme.Black
import com.acme.cabconnect.ui.theme.WhiteGrey

/**
 * Bildschirm für das Grundgerüst des Nachrichten-Screens.
 */
@Composable
fun NachrichtenScreen(navController: NavController, darkTheme: Boolean = isSystemInDarkTheme()){
    Box(modifier = Modifier
        .background(if (darkTheme) Black else WhiteGrey)
        .fillMaxSize()
    ) {
        Column {
            BoxWithConstraints {
                val maxHeight = this.maxHeight

                val topHeight: Dp = maxHeight * 2 / 5

                Heading(
                    headline = "Nachrichten",
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .height(topHeight),
                    sortAsc = true,
                    onSortChange = { },
                    filter = "",
                    onFilterChange = { }
                )
            }
        }
    }
}