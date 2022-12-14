package com.acme.cabconnect.presentation.fahrten.components

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.acme.cabconnect.ui.theme.*

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    items: List<BottomMenuContent>,
    navController: NavController,
    onItemClick: (BottomMenuContent) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier.drawBehind {
            drawLine(
                if (darkTheme) WhiteGrey else Grey,
                Offset(0f, 0f),
                Offset(size.width, 0f),
                5f
            )
        },
        backgroundColor = if (darkTheme) Black else White,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Orange,
                unselectedContentColor = if (darkTheme) White else Grey,
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                        Text(
                            text = item.title,
                            fontSize = 12.sp
                        )
                    }
                }
            )
        }
    }
}