package pg.mobile.as04state.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.update {
            val error = validateEmail(newEmail)
            it.copy(
                email = newEmail,
                emailError = error,
                isValid = validateForm(
                    newEmail,
                    it.password,
                    it.firstName,
                    it.lastName
                )
            )
        }
    }

    fun onPasswordChange(newPass: String) {
        _uiState.update {
            val error = validatePassword(newPass)
            it.copy(
                password = newPass,
                passwordError = error,
                isValid = validateForm(
                    it.email,
                    newPass,
                    it.firstName,
                    it.lastName
                )
            )
        }
    }

    fun onFirstNameChange(newName: String) {
        _uiState.update {
            val error = validateName(newName)
            it.copy(
                firstName = newName,
                firstNameError = error,
                isValid = validateForm(
                    it.email,
                    it.password,
                    newName,
                    it.lastName
                )
            )
        }
    }

    fun onLastNameChange(newName: String) {
        _uiState.update {
            val error = validateName(newName)
            it.copy(
                lastName = newName,
                lastNameError = error,
                isValid = validateForm(
                    it.email,
                    it.password,
                    it.firstName,
                    newName
                )
            )
        }
    }

    fun submit() {
        _uiState.update {
            it.copy(fullName = "${it.firstName} ${it.lastName}".trim())
        }
    }

    private fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email tidak boleh kosong"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Format email tidak valid"
            else -> null
        }
    }

    private fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password tidak boleh kosong"
            password.length < 6 -> "Minimal 6 karakter"
            else -> null
        }
    }

    private fun validateName(name: String): String? {
        return when {
            name.isBlank() -> "Tidak boleh kosong"
            else -> null
        }
    }

    private fun validateForm(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Boolean {
        return validateEmail(email) == null &&
                validatePassword(password) == null &&
                validateName(firstName) == null &&
                validateName(lastName) == null
    }
}