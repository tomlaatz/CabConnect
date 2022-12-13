package com.acme.cabconnect.presentation.fahrten.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.acme.cabconnect.ui.theme.Grey
import com.acme.cabconnect.ui.theme.Orange
import com.acme.cabconnect.ui.theme.White

@Composable
fun BottomNavigationBar(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    navController: NavController,
    onItemClick: (BottomMenuContent) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier.border(1.dp, Grey),
        backgroundColor = White,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Orange,
                unselectedContentColor = Grey,
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                            //modifier = Modifier.size(28.dp)
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