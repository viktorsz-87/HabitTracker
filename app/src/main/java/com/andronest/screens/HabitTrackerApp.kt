package com.andronest.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andronest.model.entity.Habit
import com.andronest.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitTrackerApp(
    viewModel: HabitViewModel = hiltViewModel(),
    modifier: Modifier = Modifier) {

    var showAddDialog by remember { mutableStateOf(false) }
    var newHabitName by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog=true }) {
                Icon(Icons.Default.Add,"Add Habit")
            }
        }
    ) { padding ->

        HabitList(
            modifier = Modifier.padding(padding),
            viewModel = viewModel)
    }

    if(showAddDialog){

        AlertDialog(
            title = {Text("Add New Habit")},
            onDismissRequest = { showAddDialog = false },
            confirmButton = {
                Button(onClick = {
                    viewModel.addHabit(newHabitName)
                    newHabitName=""
                    showAddDialog = false
                }) { Text("Add") }
            },
            dismissButton = {
                TextButton(onClick = {showAddDialog=false}) {
                    Text("Cancel")
                }
            },
            text = {
                OutlinedTextField(
                    value = newHabitName,
                    onValueChange = {newHabitName = it},
                    label = { Text("Habit name") }
                )
            }
        )
    }
}

@Composable
fun HabitList(modifier: Modifier = Modifier, viewModel: HabitViewModel) {

    val habits by viewModel.habits.collectAsState(initial = emptyList())

    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(habits){ habit->
            HabitItem(
                habit = habit,
                onComplete = { viewModel.completeHabit(habit.id)},
                onDelete = { viewModel.removeHabit(habit) },
                viewModel = viewModel)
        }
    }
}

@Composable
fun HabitItem(
    habit: Habit,
    onComplete: () -> Unit,
    onDelete: () -> Unit,
    viewModel: HabitViewModel) {

    val streak by viewModel.getStreak(habit.id).collectAsState(0)

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(habit.color)
            .copy(alpha = 0.2f))) {

        Row(modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Text(habit.icon, modifier = Modifier.size(40.dp))
            Spacer(Modifier.width(16.dp))

            Column{
                Text(habit.name, style = MaterialTheme.typography.titleMedium)
                Text("$streak-day streak", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.weight(1f))
            IconButton(onClick = {onComplete()}) { Icon(Icons.Default.Add, "Complete")}
            IconButton(onClick = {onDelete()}) { Icon(Icons.Default.Close, "Delete")}
        }
    }
}
