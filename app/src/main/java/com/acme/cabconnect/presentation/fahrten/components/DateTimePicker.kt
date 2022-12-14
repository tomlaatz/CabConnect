package com.acme.cabconnect.presentation.fahrten.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.acme.cabconnect.ui.theme.Grey
import com.acme.cabconnect.ui.theme.Orange
import com.acme.cabconnect.ui.theme.White
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun DatePicker(
    pickedDate: LocalDate,
    onPickedDateChange: (LocalDate) -> Unit,
    darkTheme: Boolean = isSystemInDarkTheme(),
    modifier: Modifier
) {
    val dateDialogState = rememberMaterialDialogState()

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Button(
            elevation = null,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
            ),
            onClick = {
                dateDialogState.show()
            }) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = if (darkTheme) White else Grey
            )
        }
        TextField(
            value = pickedDate.format(DateTimeFormatter.ofPattern("dd.MM.")),
            onValueChange = { },
            enabled = false,
            modifier = Modifier.clickable { dateDialogState.show() }
        )
    }

    MaterialDialog(
        dialogState = dateDialogState,
        backgroundColor = Orange,
        elevation = 8.dp,
        buttons = {
            positiveButton(text = "Ok", textStyle = TextStyle(color = White))
            negativeButton(text = "Abbrechen", textStyle = TextStyle(color = White))
        }
    ) {
        this.datepicker(
            initialDate = LocalDate.now(),
            title = "Datum auswählen",
            allowedDateValidator = { it > LocalDate.now().minusDays(1) }
        ) {
            onPickedDateChange(it)
        }
    }
}


@Composable
fun TimePicker(
    pickedTime: LocalTime,
    onPickedTimeChange: (LocalTime) -> Unit,
    modifier: Modifier
) {
    val timeDialogState = rememberMaterialDialogState()

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        TextField(
            value = pickedTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            onValueChange = { },
            enabled = false,
            modifier = Modifier.clickable { timeDialogState.show() }
        )
    }

    MaterialDialog(
        dialogState = timeDialogState,
        backgroundColor = Orange,
        elevation = 8.dp,
        buttons = {
            positiveButton(text = "Ok", textStyle = TextStyle(color = White))
            negativeButton(text = "Abbrechen", textStyle = TextStyle(color = White))
        }
    ) {
        this.timepicker(
            initialTime = pickedTime,
            title = "Uhrzeit auswählen",
            is24HourClock = true
            // colors = DatePickerDefaults.colors()
        ) {
            onPickedTimeChange(it)
        }
    }
}