package dashboard.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dashboard.navigation.Dashboard
import dashboard.ui.screen.dashboard.DashboardScreen

fun NavGraphBuilder.DashboardNavigationBuilder() {
    composable<Dashboard> {
        DashboardScreen()
    }
}