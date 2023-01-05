package com.acme.cabconnect.presentation.fahrten.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.acme.cabconnect.presentation.CabConnectEvent
import com.acme.cabconnect.presentation.CabConnectViewModel
import com.acme.cabconnect.presentation.Screen
import com.acme.cabconnect.ui.theme.*
import java.time.LocalDate

/**
 * Orangener Hintergrund mit Beschreibung der Bildschirmseite, sowie Einstellungen und ggf.
 * "Zurück-Button".
 */
@Composable
fun Heading(
    headline: String,
    modifier: Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    viewModel: CabConnectViewModel = hiltViewModel(),
    hasNavigation: Boolean = false,
    sortAsc: Boolean,
    onSortChange: (Boolean) -> Unit,
    filter: String,
    onFilterChange: (String) -> Unit,
    navController: NavController
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    if (openDialog) {
        AlertDialog(
            backgroundColor = if (darkTheme) DarkGrey else White,
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(40.dp),
            onDismissRequest = {
                openDialog = false
            },
            title = {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    Text(text = "Datenbank löschen?", fontWeight = FontWeight.Bold, color = if (darkTheme) White else Grey)
                    Button(
                        elevation = null,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        onClick = { openDialog = false }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = if (darkTheme) White else Grey
                        )
                    }
                }

            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp)
                ) {
                    Text("Bist du sicher, dass du die Datenbank löschen möchtest?", color = if (darkTheme) White else Grey)
                }
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp, bottom = 20.dp)
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            shape = RoundedCornerShape(40.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = if (darkTheme) White else Grey),
                            onClick = {
                                viewModel.onEvent(CabConnectEvent.DeleteAll)
                                openDialog = false
                                navController.navigate(Screen.MeineFahrtenScreen.route)
                            }
                        ) {
                            Text("Löschen", color = if (darkTheme) Black else White)
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            shape = RoundedCornerShape(30.dp),
                            onClick = { openDialog = false }
                        ) {
                            Text(text = "Abbrechen", color = if (darkTheme) White else Grey)
                        }
                    }

                }
            }
        )
    }

    Column(modifier.background(Orange, shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))) {
        if (hasNavigation) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp, top = 20.dp)
            ) {
                Button(
                    elevation = null,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Grey
                    )
                }


                Row {
                    if (sortAsc) {
                        Button(
                            elevation = null,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
                            ),
                            onClick = {
                                onSortChange(!sortAsc)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowUp,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Grey
                            )
                        }
                    }
                    else {
                        Button(
                            elevation = null,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
                            ),
                            onClick = {
                                onSortChange(!sortAsc)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.KeyboardArrowDown,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = Grey
                            )
                        }
                    }

                    val listItems = arrayOf("Datum", "Platz", "Rating")
                    var disabledItem by remember {
                        mutableStateOf(0)
                    }

                    // state of the menu
                    var expanded by remember {
                        mutableStateOf(false)
                    }

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                disabledBackgroundColor = Color.Transparent
                            ),
                            elevation = null,
                            onClick = {
                                expanded = true
                            }
                        ) {
                            Text(
                                text = filter,
                                color = Grey,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Icon(
                                    imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = Grey
                            )
                        }

                        // drop down menu
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            // adding items
                            listItems.forEachIndexed { itemIndex, itemValue ->
                                DropdownMenuItem(
                                    onClick = {
                                        onFilterChange(itemValue)
                                        expanded = false
                                        disabledItem = itemIndex
                                    },
                                    enabled = (itemIndex != disabledItem)
                                ) {
                                    Text(text = itemValue)
                                }
                            }
                        }
                    }
                }

                Button(
                    elevation = null,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    onClick = {
                        openDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Grey
                    )
                }

            }
        } else {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp, top = 20.dp)
            ) {
                Button(
                    elevation = null,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent,
                    ),
                    onClick = {
                        openDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = Grey
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, bottom = 150.dp, top = 30.dp)
        ) {
            Text(
                text = headline,
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                color = Grey
            )
        }
    }
}