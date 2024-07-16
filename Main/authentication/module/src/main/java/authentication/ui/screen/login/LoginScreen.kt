package authentication.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import authentication.navigation.LoginResult
import authentication.ui.form.LoginForm
import authentication.ui.form.LoginFormState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun LoginScreen(
    /** States **/
    viewModel: LoginViewModel = viewModel(),
    onSuccess: (LoginResult) -> Unit,
    onNavigateToRegistration: () -> Unit,
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current
    viewModel.collectSideEffect {
        when (it) {
            is LoginScreenEffect.LoginSuccess -> onSuccess.invoke(LoginResult())
            is LoginScreenEffect.ShowToastError -> Toast.makeText(
                context,
                it.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Login Screen")
            if (state.showLoading)
                CircularProgressIndicator()

            Spacer(modifier = Modifier.weight(1f))

            val loginFormState = remember { LoginFormState() }
            LoginForm(
                state = loginFormState,
                onFormValue = { loginFormValue ->
                    viewModel.updateFormValue(loginFormValue)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.submitLogin() },
                enabled = state.enableSubmitButton
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onNavigateToRegistration.invoke() },
                colors = ButtonDefaults.filledTonalButtonColors()
            ) {
                Text(text = "Go To Registration")
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

