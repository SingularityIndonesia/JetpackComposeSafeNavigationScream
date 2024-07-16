package authentication.ui.screen.login

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import arrow.optics.optics
import authentication.model.LoginCredential
import authentication.ui.form.LoginFormState
import authentication.util.VmIdle
import authentication.util.VmProcessing
import authentication.util.VmState
import authentication.util.VmSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@Immutable
@optics
data class LoginScreenSate(
    val submitLoadingState: VmState<LoginCredential> = VmIdle(),
    val formIsValid: Boolean = false,
) {
    companion object

    // reduce state if:
    // - state need more than one reason
    // - state have shared reference parameter
    val showLoading: Boolean get() = submitLoadingState is VmProcessing
    val enableSubmitButton: Boolean get() = formIsValid && submitLoadingState !is VmProcessing
}

sealed class LoginScreenEffect {
    data class ShowToastError(val message: String) : LoginScreenEffect()
    data class LoginSuccess(val credential: LoginCredential) : LoginScreenEffect()
}

class LoginViewModel : ContainerHost<LoginScreenSate, LoginScreenEffect>, ViewModel() {

    override val container: Container<LoginScreenSate, LoginScreenEffect> =
        container(LoginScreenSate())

    private var formValue: LoginFormState.FormValue? = null

    fun submitLogin() = intent {
        val lens = LoginScreenSate.submitLoadingState

        reduce { lens.set(state, VmProcessing()) }

        // do login stuff
        val email = formValue?.email ?: ""
        val password = formValue?.password ?: ""

        // call api
        val loginResultState = runBlocking {
            delay(3000)
            VmSuccess(LoginCredential())
        }

        // assume success
        reduce { lens.set(state, loginResultState) }

        postSideEffect(LoginScreenEffect.LoginSuccess(loginResultState.data))
    }

    fun updateFormValue(
        formValue: LoginFormState.FormValue
    ) = intent {
        this@LoginViewModel.formValue = formValue

        val lens = LoginScreenSate.formIsValid

        if (formValue.isValid != state.formIsValid)
            reduce { lens.set(state, formValue.isValid) }
    }
}