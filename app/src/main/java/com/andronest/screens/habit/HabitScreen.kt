package com.andronest.screens.habit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andronest.screens.EmptyState
import com.andronest.screens.ErrorState
import com.andronest.screens.LoadingState
import com.andronest.screens.ResultPage
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

