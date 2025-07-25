package com.andronest.screens.habit


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andronest.util.NotificationScheduler
import com.andronest.viewmodel.HabitViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDialog(
    viewModel: HabitViewModel,
    showDialog: Boolean,
    onDismissRequest: () -> Unit
) {
    var habitName by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf<Color?>(null) }
    var selectedIcon by remember { mutableStateOf<String?>(null) }
    var showColorPicker by remember { mutableStateOf(false) }
    var showIconPicker by remember { mutableStateOf(false) }

    var showTimePicker by remember { mutableStateOf(false) }
    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    if (showDialog) {

        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    enabled = habitName.isNotBlank(),
                    onClick = {
                        viewModel.addHabit(
                            habitName,
                            color = selectedColor ?: Color.Black,
                            icon = selectedIcon ?: ""
                        )
                        habitName = ""
                        onDismissRequest()
                        NotificationScheduler.scheduleDailyReminder(context, selectedHour, selectedMinute)
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    habitName = ""
                    onDismissRequest()
                }) {
                    Text("Cancel")
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Add")
            },
            title = { Text(text = "Add New Habit") },
            text = {
                Column {
                    OutlinedTextField(
                        value = habitName,
                        onValueChange = { habitName = it },
                        label = { Text(text = "Habit name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Color picker row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Color: ", modifier = Modifier.padding(end = 8.dp))
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    color = selectedColor ?: Color.Black,
                                    shape = CircleShape
                                )
                                .clickable { showColorPicker = true }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Icon picker row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("Icon: ", modifier = Modifier.padding(end = 8.dp))

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                            .size(40.dp)
                            .background(
                                shape = CircleShape,
                                color= MaterialTheme.colorScheme.surfaceVariant)
                            .clickable{ showIconPicker = true }
                        ){

                            Text(text = selectedIcon ?: "",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier
                                    .wrapContentSize() // take only needed space
                                    .padding(4.dp),
                                textAlign = TextAlign.Center)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    // Timer picker
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Icon(Icons.Default.Notifications, contentDescription = "Alarm")

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth().padding(start = 15.dp, end=15.dp)
                                .background(
                                    shape = CircleShape,
                                    color= MaterialTheme.colorScheme.surfaceVariant)
                                .clickable{ showTimePicker = true }
                        ){

                            Text(text = "${selectedHour.toString().padStart(2,'0')}:${selectedMinute.toString().padStart(2,'0')}",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier
                                    .wrapContentSize() // take only needed space
                                    .padding(4.dp),
                                textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        )
    }

    HabitReminderTimePicker(
        showDialog = showTimePicker,
        onDismiss = { showTimePicker = false },
        onTimeSelected = { hours, mins->
            selectedHour = hours
            selectedMinute = mins
            //NotificationScheduler.scheduleDailyReminder(context, hours, mins)
        }
    )

    HabitColorPickerDialog(
        showDialog = showColorPicker,
        onDismissRequest = { showColorPicker = false },
        onColorSelected = { color -> selectedColor = color }
    )

    HabitIconPickerDialog(
        showDialog = showIconPicker,
        onDismissRequest = {showIconPicker = false},
        onIconSelected = { icon-> selectedIcon = icon }
    )
}