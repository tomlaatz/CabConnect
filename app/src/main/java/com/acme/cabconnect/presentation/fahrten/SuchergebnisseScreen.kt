package com.acme.cabconnect.presentation.fahrten

import android.util.Log
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
import com.acme.cabconnect.ui.theme.Orange
import com.acme.cabconnect.ui.theme.White
import com.acme.cabconnect.ui.theme.WhiteGrey
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun SuchergebnisseScreen(
    navController: NavController,
    viewModel: CabConnectViewModel = hiltViewModel(),
    datum: Long,
    start: String,
    ziel: String,
    freierPlatz: Int
) {
    viewModel.onEvent(
        CabConnectEvent.GetSuchergebnisse(
            datum,start, ziel, freierPlatz
        )
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
                    headline = "Suchergebnisse",
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .height(topHeight),
                    hasNavigation = true
                )

                Suchergebnisse(
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
fun Suchergebnisse(
    viewModel: CabConnectViewModel = hiltViewModel(),
    modifier: Modifier,
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value

    Log.d("In Suchergebnisse: ", "State: ${state.fahrten}")

    LazyColumn(
        modifier = modifier.padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(state.fahrten) {
            suchergebnis ->
            SuchergebnisItem(suchergebnis, navController = navController)
        }
    }
}

@Composable
fun SuchergebnisItem(suchergebnis: MitgliederEinerFahrt, viewModel: CabConnectViewModel = hiltViewModel(), navController: NavController) {
    val formatter = DateTimeFormatter.ofPattern("EEEE, dd.MM.").withLocale(Locale.GERMANY)
    val formatterAbfahrt = DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.GERMANY)
    var openDialog by remember {
        mutableStateOf(false)
    }

    if (openDialog) {
        AlertDialog(
            shape = RoundedCornerShape(40.dp),
            onDismissRequest = {
                openDialog = false
            },
            title = {
                Text(text = "Fahrt beitreten?", fontWeight = FontWeight.Bold, color = Grey)
            },
            text = {
                Column {
                    Text("Um dieser Fahrt beitreten zu können müssen Sie eine Gebühr von 0.50€ zahlen.", color = Grey)
                }
            },
            buttons = {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp, end = 50.dp, bottom = 20.dp)
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = {
                                viewModel.onEvent(CabConnectEvent.JoinFahrt(suchergebnis.fahrt))
                                openDialog = false
                                navController.navigate(Screen.MeineFahrtenScreen.route)
                            }
                        ) {
                            Text("Zahlen")
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Grey),
                            onClick = { openDialog = false }
                        ) {
                            Text(text = "Abbrechen", color = White)
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
                    text =  Instant.ofEpochMilli(suchergebnis.fahrt.datum).atZone(ZoneId.of("Europe/Berlin")).toLocalDate().format(formatter),
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
                        text = suchergebnis.fahrt.start,
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
                        text = suchergebnis.fahrt.ziel,
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
                    text = Instant.ofEpochMilli(suchergebnis.fahrt.datum).atZone(ZoneId.of("Europe/Berlin")).toLocalDateTime().format(formatterAbfahrt),
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
                    text = "Freier Platz: ",
                    color = Grey,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = suchergebnis.fahrt.freierPlatz.toString(),
                    color = Grey,
                    modifier = Modifier.padding(5.dp)
                )
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Grey
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 45.dp)
            ) {
                Column {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = Grey,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = suchergebnis.users[0].username,
                        color = Grey,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = suchergebnis.users[0].bewertung.toString(),
                            color = Grey
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Orange,
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp, start = 40.dp, end = 40.dp)
            ) {
                Button(
                    enabled = suchergebnis.users[0].userId != 1,
                    onClick = { openDialog = true },
                    shape = RoundedCornerShape(40.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Beitreten",
                        color = Grey,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }


    }
}