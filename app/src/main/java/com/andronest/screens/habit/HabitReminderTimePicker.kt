package com.andronest.screens.habit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.util.Calendar

@Composable
fun HabitReminderTimePicker(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    modifier: Modifier = Modifier) {

    var selectedHour by remember {
        mutableIntStateOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
    }

    var selectedMinute by remember {
        mutableIntStateOf(Calendar.getInstance().get(Calendar.MINUTE))
    }

    if(showDialog){

        Dialog(onDismissRequest = onDismiss) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                shape = MaterialTheme.shapes.extraLarge){

                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally){

                    Text("Set Reminder Time", style = MaterialTheme.typography.titleLarge)

                    Spacer(Modifier.height(24.dp))

                    // Time picker
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        NumberPicker(
                            value = selectedHour,
                            onValueChange = {selectedHour = it},
                            range = 0..23,
                            label = "Hour"
                        )

                        Text(":", style = MaterialTheme.typography.displaySmall)

                        NumberPicker(
                            value = selectedMinute,
                            onValueChange = {selectedMinute = it},
                            range = 0..59,
                            label = "Minute"
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            onTimeSelected(selectedHour, selectedMinute)
                            onDismiss()
                        }
                    ){
                        Text("Set Reminder")
                    }
                }
            }
        }
    }
}

@Composable
fun NumberPicker(value: Int,
                 onValueChange: (Int) -> Unit,
                 range: IntRange,
                 label: String) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(label, style = MaterialTheme.typography.labelSmall)

        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = {
                if(value < range.last) onValueChange(value+1)
            }) {
                Icon(Icons.Default.KeyboardArrowUp, "Increase")
            }

            Text(
                text = value.toString().padStart(2,'0'),
                style= MaterialTheme.typography.displayMedium)

            IconButton(onClick = {
                if(value > range.first) onValueChange(value-1)
            }) {
                Icon(Icons.Default.KeyboardArrowDown, "Decrease")
            }
        }
    }
}