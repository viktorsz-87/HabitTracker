package com.andronest.navigation

sealed class Navigation(val route: String){

    object Home: Navigation("Home")
    object Analytics: Navigation("Analytics/{habitId}"){
        fun createRoute(habitId: Int): String {
            return "Analytics/$habitId"
        }
    }

    fun getBaseRoute() = route.substringBefore("/")
}