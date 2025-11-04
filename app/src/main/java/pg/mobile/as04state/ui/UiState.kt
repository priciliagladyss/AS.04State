package pg.mobile.as04state.ui


data class UiState(
        val email: String = "",
        val password: String = "",
        val firstName: String = "",
        val lastName: String = "",
        val fullName: String = "",
        val emailError: String? = null,
        val passwordError: String? = null,
        val firstNameError: String? = null,
        val lastNameError: String? = null,
        val isValid: Boolean = false
)
