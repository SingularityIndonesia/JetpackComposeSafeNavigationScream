package authentication.ui.screen.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import authentication.navigation.RegistrationResult

@Composable
fun RegistrationScreen(
    onNavigateBack: () -> Unit,
    onSuccess: (RegistrationResult) -> Unit,
) {
    Column {
        Text(text = "Registration Screen")
        Button(onClick = onNavigateBack) {
            Text(text = "Back")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // do what ever
                onSuccess.invoke(RegistrationResult())
            }
        ) {
            Text(text = "SignIn")
        }
    }
}