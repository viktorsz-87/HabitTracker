package com.andronest.screens.habit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.andronest.model.HabitWithCompletions
import com.andronest.room.entity.Completion
import com.andronest.room.entity.Habit

@Composable
fun HabitItem(
    onComplete: () -> Unit = {},
    onDelete: () -> Unit = {},
    item: HabitWithCompletions,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        // Header row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            // Habit icon
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = Color(item.habit.color).copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            ) {
                Text(
                    text = item.habit.iconOrInitial(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(item.habit.color)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Habit name and streak
            Column(modifier = Modifier.wrapContentSize()) {
                Text(
                    text = item.habit.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${item.completion.size} completions",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.wrapContentSize()) {
                // Streak Counter
                BadgedBox(
                    badge = {
                        Badge(
                            modifier.padding(start = 15.dp),
                            containerColor = Color(item.habit.color).copy(alpha = 0.3f),
                            contentColor = Color(item.habit.color)
                        ) {

                            Text(
                                text = "${calculateStreak(item.completion)} day streak",
                            )
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Streak",
                        tint = Color(item.habit.color)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = { calculateCompletionRate(item.completion) },
                modifier = Modifier
                    .padding(top = 25.dp)
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(item.habit.color),
                trackColor = Color(item.habit.color).copy(alpha = 0.1f),
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilledTonalButton(
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 1.dp),
                shape = RectangleShape,
                onClick = onComplete,
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(item.habit.color).copy(alpha = 0.2f)
                )
            ) {
                Icon(Icons.Default.Check, contentDescription = "Complete")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Complete", style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }

    }
}

fun Habit.iconOrInitial(): String {
    return if (!icon.isNullOrEmpty()) icon else name.take(1).uppercase()
}

fun calculateStreak(completions: List<Completion>): Int {
    // Your streak calculation logic
    return completions.size // Simplified for example
}

fun calculateCompletionRate(completions: List<Completion>): Float {
    // Your completion rate logic
    return minOf(completions.size / 7f, 0f) // Example: weekly completion
}