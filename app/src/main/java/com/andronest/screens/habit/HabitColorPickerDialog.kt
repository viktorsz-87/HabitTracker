package com.andronest.screens.habit


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val habitColors = listOf(
    Color(0xFFF44336), // Red
    Color(0xFF2196F3), // Blue
    Color(0xFF4CAF50), // Green
    Color(0xFFFFEB3B), // Yellow
    Color(0xFF9C27B0), // Purple
    Color(0xFFFF9800), // Orange
    Color(0xFF795548), // Brown
    Color(0xFF607D8B)  // Gray
)

@Composable
fun HabitColorPickerDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedColor by remember { mutableStateOf<Color?>(null) }

    if (showDialog) {

        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text("Choose a color") },
            text = {
                Column {
                    Text(
                        "Select a color for your habit",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Color grid
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        items(habitColors) { color ->
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(color = color, shape = CircleShape)
                                    .border(
                                        width = if (selectedColor == color) 3.dp else 0.dp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        shape = CircleShape,
                                    )
                                    .clickable {
                                        selectedColor = color
                                    }
                            )
                        }
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest){
                    Text("Cancel")
                }
            },
            confirmButton = {
                Button(
                    enabled = selectedColor != null,
                    onClick = {
                        selectedColor?.let { onColorSelected(it) }
                        onDismissRequest()
                    }) {
                    Text("Confirm")
                }
            },
        )
    }
}