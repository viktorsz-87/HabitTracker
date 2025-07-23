package com.andronest.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andronest.model.HabitWithCompletions

@Composable
fun ResultPage(
    habits: List<HabitWithCompletions>,
    modifier: Modifier = Modifier) {

    LazyColumn(modifier = Modifier.fillMaxSize()) {

        items(items = habits){item->

            HabitCard(item)
        }
    }
}