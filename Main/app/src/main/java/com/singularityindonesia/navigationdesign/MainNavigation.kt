package com.singularityindonesia.navigationdesign

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import authentication.AuthenticationNavigationBuilder
import authentication.navigation.Authentication
import authentication.navigation.Registration
import dashboard.navigation.Dashboard
import dashboard.ui.DashboardNavigationBuilder

@Composable
fun MainNavigation() {
    val controller = rememberNavController()

    NavHost(
        navController = controller,
        startDestination = Authentication
    ) {
        AuthenticationNavigationBuilder(
            onLoginSuccess = {
                controller.navigate(Dashboard)
            },
            onNavigateToRegistration = {
                controller.navigate(Registration)
            },
            onRegistrationBack = {
                controller.popBackStack()
            },
            onRegistrationSuccess = {
                controller.navigate(Dashboard)
            }
        )

        DashboardNavigationBuilder()
    }
}