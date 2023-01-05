package com.acme.cabconnect.presentation.fahrten

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.acme.cabconnect.presentation.Screen
import com.acme.cabconnect.presentation.fahrten.components.DatePicker
import com.acme.cabconnect.presentation.fahrten.components.Heading
import com.acme.cabconnect.ui.theme.*
import java.time.LocalDate
import java.time.ZoneId

/**
 * Bildschirm mit dem Grundgerüst für die Übersicht des Suchformulars.
 */
@Composable
fun SuchformularScreen(
    navController: NavController,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val focusManager = LocalFocusManager.current
    Box(modifier = Modifier
        .background(if (darkTheme) Black else WhiteGrey)
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }
    ) {
        Column {
            BoxWithConstraints {
                val maxHeight = this.maxHeight

                val topHeight: Dp = maxHeight * 2 / 4

                val centerPaddingBottom = maxHeight * 1 / 4

                Heading(
                    headline = "Finde die Fahrt \ndeiner Wahl",
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

                Suchformular(
                    navController = navController,
                    modifier = Modifier
                        .padding(top = centerPaddingBottom, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }

}

/**
 * Bildschirm mit dem Formular für das Suchen einer bestimmten Fahrt unter möglicher Angabe von
 * bestimmten Filtern:
 * Startort, Zielort, Datum, Personen (Platz der nötig sein muss im Taxi).
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Suchformular(
    navController: NavController,
    modifier: Modifier,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 60.dp)
    ) {
        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(40.dp)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 45.dp)
                ) {
                    Text(text = "Von:", fontWeight = FontWeight.Bold, color = if (darkTheme) White else Grey)
                }

                var startOrt by remember {
                    mutableStateOf("")
                }


                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 45.dp, end = 45.dp)
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
                        .padding(top = 5.dp, start = 45.dp)
                ) {
                    Text(text = "Nach:", fontWeight = FontWeight.Bold, color = if (darkTheme) White else Grey)
                }

                var zielOrt by remember {
                    mutableStateOf("")
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 45.dp, end = 45.dp)
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
                        .padding(top = 5.dp, start = 45.dp)
                ) {
                    Text(text = "Datum:", fontWeight = FontWeight.Bold, color = if (darkTheme) White else Grey)
                }

                var pickedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 45.dp, end = 45.dp)
                ) {
                    DatePicker(modifier = Modifier.weight(1f), pickedDate = pickedDate, onPickedDateChange = { pickedDate = it })
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, start = 45.dp)
                ) {
                    Text(text = "Personen:", fontWeight = FontWeight.Bold, color = if (darkTheme) White else Grey)
                }

                var platz by remember {
                    mutableStateOf("1")
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 45.dp, end = 45.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            elevation = null,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent,
                            ),
                            onClick = {
                            }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(30.dp),
                                tint = if (darkTheme) White else Grey
                            )
                        }

                        val maxChar = 2

                        TextField(
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            value = platz,
                            onValueChange = {
                                if (it.length <= maxChar) {
                                    platz = it
                                }
                            },
                            singleLine = true,
                            keyboardActions = KeyboardActions(
                                onDone = {keyboardController?.hide()})
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp, start = 30.dp, end = 30.dp, bottom = 30.dp),
                        shape = RoundedCornerShape(40.dp),
                        onClick = {
                            navController.navigate(
                                Screen.SuchergebnisseScreen.route +
                                        "?datum=${pickedDate.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant().toEpochMilli()}&start=${startOrt.trim().uppercase()}&ziel=${zielOrt.trim().uppercase()}&freierPlatz=$platz"
                            )
                        }) {
                        Text(
                            text = "Suchen",
                            color = if (darkTheme) White else Grey,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

}
