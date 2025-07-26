package com.andronest.screens.habit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.andronest.model.HabitWithCompletions
import com.andronest.viewmodel.HabitViewModel

@Composable
fun HabitCard(
    item: HabitWithCompletions,
    viewModel: HabitViewModel,
    navController: NavController,
    modifier: Modifier = Modifier) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(item.habit.color).copy(alpha = 0.2f),
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, Color(item.habit.color).copy(alpha = 0.5f)),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {

        HabitItem(
            item = item,
            onDelete = { viewModel.removeHabit(item.habit) },
            onComplete = { viewModel.addCompletion(item.habit.id) },
            navController = navController
        )
    }
}