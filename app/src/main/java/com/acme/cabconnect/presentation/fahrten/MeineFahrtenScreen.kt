package com.acme.cabconnect.presentation.fahrten

import androidx.compose.foundation.background
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
import com.acme.cabconnect.ui.theme.Grey
import com.acme.cabconnect.ui.theme.White
import com.acme.cabconnect.ui.theme.WhiteGrey
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun MeineFahrtenScreen(
    navController: NavController,
    viewModel: CabConnectViewModel = hiltViewModel()
) {
    viewModel.onEvent(
        CabConnectEvent.GetMeineFahrten(1)
    )

    Box(modifier = Modifier
        .background(WhiteGrey)
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
                        .height(topHeight)
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

@Composable
fun MeineFahrten(viewModel: CabConnectViewModel = hiltViewModel(), modifier: Modifier, navController: NavController) {
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


@Composable
fun FahrtItem(fahrtItem: MitgliederEinerFahrt, viewModel: CabConnectViewModel = hiltViewModel(), navController: NavController) {
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
                    Text(text = "Fahrt löschen?", fontWeight = FontWeight.Bold, color = Grey)
                    Button(
                        elevation = null,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        onClick = { openLoeschenDialog = false }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Grey
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
                    Text("Bist du sicher, dass du die Fahrt löschen möchtest?", color = Grey)
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
                            colors = ButtonDefaults.buttonColors(backgroundColor = Grey),
                            onClick = {
                                viewModel.onEvent(CabConnectEvent.DeleteFahrt(fahrtItem.fahrt))
                                openLoeschenDialog = false
                                navController.navigate(Screen.MeineFahrtenScreen.route)
                            }
                        ) {
                            Text("Löschen ", color = White)
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
                            Text(text = "Abbrechen", color = Grey)
                        }
                    }

                }
            }
        )
    }


    if (openDialog) {
        AlertDialog(
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
                    Text(text = "Fahrt austreten?", fontWeight = FontWeight.Bold, color = Grey)
                    Button(
                        elevation = null,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        onClick = { openDialog = false }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Grey
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
                    Text("Bist du sicher, dass du der Fahrt austreten möchtest?", color = Grey)
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
                            colors = ButtonDefaults.buttonColors(backgroundColor = Grey),
                            onClick = {
                                viewModel.onEvent(CabConnectEvent.LeaveFahrt(fahrtItem.fahrt))
                                openDialog = false
                                navController.navigate(Screen.MeineFahrtenScreen.route)
                            }
                        ) {
                            Text("Austreten", color = White)
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
                            Text(text = "Abbrechen", color = Grey)
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
                    color = Grey,
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
                        color = Grey,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = fahrtItem.fahrt.start,
                        color = Grey
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Nach: ",
                        color = Grey,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = fahrtItem.fahrt.ziel,
                        color = Grey
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
                    color = Grey,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = Instant.ofEpochMilli(fahrtItem.fahrt.datum).atZone(ZoneId.of("Europe/Berlin")).toLocalDateTime().format(formatterAbfahrt),
                    color = Grey
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
                    color = Grey,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = fahrtItem.users.size.toString(),
                    color = Grey
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
                        backgroundColor = Grey,
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

