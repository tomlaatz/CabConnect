package com.acme.cabconnect.presentation.fahrten

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.acme.cabconnect.domain.model.Fahrt
import com.acme.cabconnect.presentation.CabConnectEvent
import com.acme.cabconnect.presentation.CabConnectViewModel
import com.acme.cabconnect.presentation.Screen
import com.acme.cabconnect.presentation.fahrten.components.DatePicker
import com.acme.cabconnect.presentation.fahrten.components.Heading
import com.acme.cabconnect.presentation.fahrten.components.TimePicker
import com.acme.cabconnect.ui.theme.Grey
import com.acme.cabconnect.ui.theme.WhiteGrey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@Composable
fun ErstellenScreen(navController: NavController) {
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
                    headline = "Erstelle deine Fahrt",
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .height(topHeight)
                )

                ErstellenFormular(
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ErstellenFormular(modifier: Modifier, viewModel: CabConnectViewModel = hiltViewModel(), navController: NavController) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(40.dp),
        modifier = modifier
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 45.dp)
            ) {
                Text(text = "Wo fährst du los?:", fontWeight = FontWeight.Bold, color = Grey)
            }

            var startOrt by remember {
                mutableStateOf("")
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                TextField(
                    value = startOrt,
                    onValueChange = { startOrt = it },
                    placeholder = { Text(text = "Startort angeben") },
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {keyboardController?.hide()})
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 45.dp)
            ) {
                Text(text = "Wohin fährst du?", fontWeight = FontWeight.Bold, color = Grey)
            }

            var zielOrt by remember {
                mutableStateOf("")
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                TextField(
                    value = zielOrt,
                    onValueChange = { zielOrt = it },
                    placeholder = { Text(text = "Zielort angeben") },
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {keyboardController?.hide()})
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 45.dp)
            ) {
                Text(text = "Wann fährst du?", fontWeight = FontWeight.Bold, color = Grey)
            }

            var pickedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
            var pickedTime by rememberSaveable { mutableStateOf(LocalTime.MIDNIGHT) }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 45.dp, end = 45.dp)
            ) {
                DatePicker(modifier = Modifier.weight(2.5f), pickedDate = pickedDate, onPickedDateChange = { pickedDate = it })
                Spacer(modifier = Modifier.width(8.dp))
                TimePicker(modifier = Modifier.weight(1f), pickedTime = pickedTime, onPickedTimeChange = { pickedTime = it })
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 45.dp)
            ) {
                Text(text = "Wieviel Platz ist frei?", fontWeight = FontWeight.Bold, color = Grey)
            }

            var platz by remember {
                mutableStateOf("1")
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 45.dp, end = 45.dp)
            ) {
                val maxChar = 2

                TextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = platz,
                    onValueChange = {
                        if (it.length <= maxChar) {
                            platz = it
                        }
                    },
                    modifier = Modifier.width(60.dp),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = {keyboardController?.hide()})
                )
                Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                Text("Person(en)")
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Grey
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Button(
                    enabled = !(startOrt.isEmpty() || zielOrt.isEmpty()),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    shape = RoundedCornerShape(40.dp),
                    onClick = {
                        val dateTime = LocalDateTime.of(pickedDate, pickedTime)

                        val zdt = dateTime.atZone(ZoneId.of("Europe/Berlin"))

                        viewModel.onEvent(CabConnectEvent.CreateFahrt(
                            fahrt = Fahrt(
                                datum = zdt.toInstant().toEpochMilli(),
                                start = startOrt,
                                ziel = zielOrt,
                                freierPlatz = platz.toInt()
                            ),
                            userId = 1
                        ))
                        navController.navigate(Screen.MeineFahrtenScreen.route)
                    }) {
                    Text(
                        text = "Fahrt erstellen",
                        color = Grey,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    }
}