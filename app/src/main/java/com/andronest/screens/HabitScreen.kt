package com.andronest.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andronest.viewmodel.HabitViewModel

@Composable
fun HabitScreen(
    viewModel: HabitViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->

        HabitDialog(
            showDialog = showDialog,
            viewModel = viewModel,
            onDismissRequest = { showDialog = false }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when {
                uiState.error != null -> ErrorState(uiState.error ?: "Unknown error")
                uiState.isLoading -> LoadingState()
                !uiState.habits.isNullOrEmpty() -> ResultPage(uiState.habits)
                else -> EmptyState()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitDialog(
    viewModel: HabitViewModel,
    showDialog: Boolean,
    onDismissRequest: () -> Unit
) {
    var habitName by remember { mutableStateOf("") }

    if (showDialog) {

        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    enabled = habitName.isNotBlank(),
                    onClick = {
                        viewModel.addHabit(habitName)
                        habitName=""
                        onDismissRequest()
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    habitName=""
                    onDismissRequest()
                }) {
                    Text("Cancel")
                }
            },
            icon = {
                Icon(imageVector =  Icons.Default.Check, contentDescription = "Add")
            },
            title = { Text(text = "Add New Habit") },
            text = {
                Column {
                    OutlinedTextField(
                        value = habitName,
                        onValueChange = {habitName = it},
                        label = {Text(text = "Habit name")},
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        )
    }
}