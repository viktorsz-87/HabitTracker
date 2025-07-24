package com.andronest.screens.habit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HabitIconPickerDialog(
    onDismissRequest: () -> Unit,
    onIconSelected: (String?) -> Unit,
    showDialog: Boolean,
    modifier: Modifier = Modifier
) {

    val habitIcons = listOf("🏃", "📚", "💧", "🍎", "🛌", "🧘", "🚭", "📱")

    var selectedIcon by remember { mutableStateOf("") }

    if (showDialog) {

        AlertDialog(
            title = { Text("Choose Habit Icon") },
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    enabled = !selectedIcon.isNullOrEmpty(),
                    onClick = {
                        onIconSelected(selectedIcon)
                        onDismissRequest()
                    })
                { Text("Select") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        selectedIcon = ""
                        onDismissRequest()
                    })
                { Text("Cancel") }
            },
            text = {
                Column(modifier = modifier.fillMaxWidth()) {

                    Spacer(modifier = modifier.height(16.dp))

                    LazyVerticalGrid(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        columns = GridCells.Fixed(4)
                    ) {
                        items(habitIcons) { icon ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        color = if (selectedIcon == icon) Color.Green.copy(
                                            alpha = 0.2f
                                        ) else Color.White
                                    )
                                    .border(
                                        width = if (selectedIcon == icon) 3.dp else 0.dp,
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    .clickable {
                                        selectedIcon = icon
                                    }
                            ){
                                Text(icon, fontSize = 50.sp)
                            }
                        }
                    }
                }
            }
        )
    }
}