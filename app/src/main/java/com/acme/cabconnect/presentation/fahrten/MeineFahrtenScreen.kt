package com.acme.cabconnect.presentation.fahrten

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.acme.cabconnect.domain.model.MitgliederEinerFahrt
import com.acme.cabconnect.presentation.CabConnectEvent
import com.acme.cabconnect.presentation.CabConnectViewModel
import com.acme.cabconnect.presentation.Screen
import com.acme.cabconnect.presentation.fahrten.components.Heading
import com.acme.cabconnect.ui.theme.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Bildschirm mit dem Grundgerüst für die Übersicht meiner Fahrten.
 */
@Composable
fun MeineFahrtenScreen(
    navController: NavController,
    viewModel: CabConnectViewModel = hiltViewModel(),
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    viewModel.onEvent(
        CabConnectEvent.GetMeineFahrten(1)
    )

    Box(modifier = Modifier
        .background(if (darkTheme) Black else WhiteGrey)
        .fillMaxSize()
    ) {
        Column {
            BoxWithConstraints {
                val maxHeight = this.maxHeight

                val topHeight: Dp = maxHeight * 2 / 5

                val centerPaddingBottom = maxHeight * 1 / 5

                Heading(
                    headline = "Meine Fahrten",
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

                MeineFahrten(
                    modifier = Modifier
                        .padding(top = centerPaddingBottom, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    navController = navController
                )
            }
        }
    }
}

/**
 * Darstellung der Liste der eigenen Fahrten bestehend aus sogenannten "Fahrt-Items"
 */
@Composable
fun MeineFahrten(
    viewModel: CabConnectViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController
) {
    viewModel.onEvent(CabConnectEvent.GetMeineFahrten(1))
    val state = viewModel.state.collectAsState().value
    LazyColumn(
        modifier = modifier.padding(bottom = 60.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(state.meineFahrten) {
                fahrt ->
            FahrtItem(fahrt, navController = navController)
        }
    }
}

/**
 * Einzelne Fahrt, welche die Informationen Datum, Startort, Zielort, Abfahrtszeit und Teil-
 * nehmeranzahl angibt.
 * Wenn die Fahrt von mir selbst erstellt wurde, dann kann ich diese hier löschen.
 * Falls ich der Fahrt beigetreten bin kann ich diser hier auch wieder austreten.
 * Die Liste ist sortiert nach Datum/Abfahrt (nach dem jetzigen Zeitpunkt).
 */
@Composable
fun FahrtItem(
    fahrtItem: MitgliederEinerFahrt,
    darkTheme: Boolean = isSystemInDarkTheme(),
    viewModel: CabConnectViewModel = hiltViewModel(),
    navController: NavController
) {
    val formatter = DateTimeFormatter.ofPattern("EEEE, dd.MM.").withLocale(Locale.GERMANY)
    val formatterAbfahrt = DateTimeFormatter.ofPattern("HH:mm")
    var openDialog by remember {
        mutableStateOf(false)
    }
    var openLoeschenDialog by remember {
        mutableStateOf(false)
    }

    if (openLoeschenDialog) {
        AlertDialog(
            backgroundColor = if (darkTheme) DarkGrey else White,
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(40.dp),
            onDismissRequest = {
                openLoeschenDialog = false
            },
            title = {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    Text(text = "Fahrt löschen?", fontWeight = FontWeight.Bold, color = if (darkTheme) White else Grey)
                    Button(
                        elevation = null,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        onClick = { openLoeschenDialog = false }
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
                    Text("Bist du sicher, dass du die Fahrt löschen möchtest?", color = if (darkTheme) White else Grey)
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
                                viewModel.onEvent(CabConnectEvent.DeleteFahrt(fahrtItem.fahrt))
                                openLoeschenDialog = false
                                navController.navigate(Screen.MeineFahrtenScreen.route)
                            }
                        ) {
                            Text("Löschen ", color = if (darkTheme) Black else White)
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            shape = RoundedCornerShape(30.dp),
                            onClick = { openLoeschenDialog = false }
                        ) {
                            Text(text = "Abbrechen", color = if (darkTheme) White else Grey)
                        }
                    }

                }
            }
        )
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
                    Text(text = "Fahrt austreten?", fontWeight = FontWeight.Bold, color = if (darkTheme) White else Grey)
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
                    Text("Bist du sicher, dass du der Fahrt austreten möchtest?", color = if (darkTheme) White else Grey)
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
                                viewModel.onEvent(CabConnectEvent.LeaveFahrt(fahrtItem.fahrt))
                                openDialog = false
                                navController.navigate(Screen.MeineFahrtenScreen.route)
                            }
                        ) {
                            Text("Austreten", color = if (darkTheme) Black else White)
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

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(40.dp),
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 45.dp)
            ) {
                Text(
                    text = Instant.ofEpochMilli(fahrtItem.fahrt.datum).atZone(ZoneId.of("Europe/Berlin")).toLocalDate().format(formatter),
                    fontSize = 26.sp,
                    color = if (darkTheme) White else Grey,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Von: ",
                        color = if (darkTheme) White else Grey,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = fahrtItem.fahrt.start,
                        color = if (darkTheme) White else Grey
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Nach: ",
                        color = if (darkTheme) White else Grey,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = fahrtItem.fahrt.ziel,
                        color = if (darkTheme) White else Grey
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 45.dp)
            ) {
                Text(
                    text = "Abfahrt: ",
                    color = if (darkTheme) White else Grey,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = Instant.ofEpochMilli(fahrtItem.fahrt.datum).atZone(ZoneId.of("Europe/Berlin")).toLocalDateTime().format(formatterAbfahrt),
                    color = if (darkTheme) White else Grey
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 45.dp)
            ) {
                Text(
                    text = "Teilnehmer: ",
                    color = if (darkTheme) White else Grey,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = fahrtItem.users.size.toString(),
                    color = if (darkTheme) White else Grey
                )
            }


            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp, start = 40.dp, end = 40.dp)
            ) {
                Button(
                    onClick = {
                        if(fahrtItem.users[0].userId == 1) {
                            openLoeschenDialog = true
                        } else {
                            openDialog = true
                        }
                    },
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (darkTheme) Orange else Grey,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                    ) {
                        Text(
                            text = if (fahrtItem.users[0].userId == 1) "Löschen" else "Austreten",
                            color = White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
            }
        }
    }
}

