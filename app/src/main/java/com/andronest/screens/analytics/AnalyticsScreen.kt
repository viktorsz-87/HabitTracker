package com.andronest.screens.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.andronest.chart.WeeklyHabitChart
import com.andronest.screens.EmptyState
import com.andronest.screens.ErrorState
import com.andronest.screens.LoadingState
import com.andronest.viewmodel.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    habitId: Int,
    viewModel: HabitViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getHabitWithCompletions(habitId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Analytics", maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when {
                uiState.error != null -> ErrorState(uiState.error ?: "Unknown error")
                uiState.isLoading -> LoadingState()
                !uiState.habits.isNullOrEmpty() ->
                    WeeklyHabitChart(
                    habitId = habitId,
                    completions = uiState.habits,
                    viewModel = viewModel
                )
                else -> EmptyState()
            }
        }
    }
}