package com.acme.cabconnect.presentation.fahrten.components

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Einzelteile der unteren Navigationsmenuelemente
 */
data class BottomMenuContent(
    val title: String,
    val icon: ImageVector,
    val route: String
)
