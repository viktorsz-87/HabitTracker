package com.andronest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andronest.navigation.Navigation
import com.andronest.screens.analytics.AnalyticsScreen
import com.andronest.screens.habit.HabitScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            NavHost(navController, startDestination = Navigation.Home.route) {

                composable(route = Navigation.Home.route) {
                    HabitScreen(navController = navController)
                }

                composable(
                    route = Navigation.Analytics.route,
                    arguments = listOf(
                        navArgument("habitId") {
                            nullable = false
                            type = NavType.IntType
                        }
                    )
                )
                { backStackEntry ->

                    val habitId = backStackEntry.arguments?.getInt("habitId")
                    habitId?.let { AnalyticsScreen(habitId = habitId, navController=navController) }
                }
            }
        }
    }
}
