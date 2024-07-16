package authentication

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import authentication.navigation.Authentication
import authentication.navigation.Login
import authentication.navigation.LoginResult
import authentication.navigation.Registration
import authentication.navigation.RegistrationResult
import authentication.ui.screen.login.LoginScreen
import authentication.ui.screen.registration.RegistrationScreen

fun NavGraphBuilder.AuthenticationNavigationBuilder(
    onLoginSuccess: (LoginResult) -> Unit,
    onNavigateToRegistration: () -> Unit,
    onRegistrationBack: () -> Unit,
    onRegistrationSuccess: (RegistrationResult) -> Unit,
) {
    navigation<Authentication>(
        startDestination = Login
    ) {
        composable<Login> { backstackEntry ->
            LoginScreen(
                onSuccess = onLoginSuccess,
                onNavigateToRegistration = onNavigateToRegistration
            )
        }

        composable<Registration> {
            RegistrationScreen(
                onNavigateBack = onRegistrationBack,
                onSuccess = onRegistrationSuccess
            )
        }
    }
}