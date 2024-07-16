package authentication.ui.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import authentication.util.EmailPattern

@Immutable
class LoginFormState {

    @Immutable
    data class FormValue(
        val email: String,
        val password: String,
        val isValid: Boolean
    )

    private val _email: MutableState<String> = mutableStateOf("")
    private val _password: MutableState<String> = mutableStateOf("")

    var email: String
        get() = _email.value
        set(value) {
            _email.value = value
        }

    val emailError: String
        get() = when {
            !email.matches(EmailPattern.toRegex()) -> "Format Salah"
            else -> ""
        }

    // reduced values
    val emailIsError: Boolean
        get() = emailError.isNotBlank() && _email.value.isNotBlank()

    var password: String
        get() = _password.value
        set(value) {
            _password.value = value
        }

    val passwordError: String
        get() = when {
            password.length < 10 -> "Pasword kurang panjang"
            else -> ""
        }

    val passwordIsError: Boolean
        get() = passwordError.isNotBlank() && _password.value.isNotBlank()

    val isValid: Boolean
        get() = emailError.isBlank() && passwordError.isBlank()

    val formValue: FormValue
        get() = FormValue(email, password, isValid)
}

@Composable
fun LoginForm(
    state: LoginFormState = LoginFormState(),
    onFormValue: (LoginFormState.FormValue) -> Unit = {}
) {
    Column {
        TextField(
            value = state.email,
            onValueChange = {
                state.email = it
            },
            isError = state.emailIsError,
        )
        if (state.emailIsError)
            Text(text = state.emailError, color = MaterialTheme.colorScheme.error)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = state.password,
            onValueChange = {
                state.password = it
            },
            isError = state.passwordIsError
        )
        if (state.passwordIsError)
            Text(text = state.passwordError, color = MaterialTheme.colorScheme.error)
    }

    LaunchedEffect(state.formValue) {
        onFormValue(state.formValue)
    }
}