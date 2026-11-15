package com.example.haircare.navigation.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.haircare.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<RegisterUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onNameChanged(name: String) {
        _uiState.update { currentState ->
            validateForm(currentState.copy(name = name))
        }
    }

    fun onEmailChanged(email: String) {
        _uiState.update { currentState ->
            validateForm(currentState.copy(email = email))
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { currentState ->
            validateForm(currentState.copy(password = password))
        }
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _uiState.update { currentState ->
            validateForm(
                currentState.copy(confirmPassword = confirmPassword)
            )
        }
    }

    // Validación del nombre
    private fun validateName(name: String): Int? {
        return when {
            name.isBlank() -> R.string.error_name
            else -> null
        }
    }

    // Validación del correo
    private fun validateEmail(email: String): Int? {
        return when {
            email.isEmpty() -> null
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                R.string.error_email
            else -> null
        }
    }

    // Validación de la contraseña
    private fun validatePassword(password: String): Int? {
        return when {
            password.isEmpty() -> null
            password.length < 8 -> R.string.error_password
            else -> null
        }
    }

    // Comprueba que las contraseñas coinciden
    private fun validateConfirmPassword(
        password: String,
        confirmPassword: String
    ): Int? {
        return when {
            confirmPassword.isEmpty() -> null
            password != confirmPassword ->
                R.string.error_confirm_password
            else -> null
        }
    }

    // Comprueba todos los campos del formulario
    private fun validateForm(state: RegisterUiState): RegisterUiState {

        val nameError = validateName(state.name)
        val emailError = validateEmail(state.email)
        val passwordError = validatePassword(state.password)

        val confirmPasswordError = validateConfirmPassword(
            state.password,
            state.confirmPassword
        )

        val isValid = state.name.isNotBlank() &&
                state.email.isNotBlank() &&
                state.password.isNotBlank() &&
                state.confirmPassword.isNotBlank() &&
                nameError == null &&
                emailError == null &&
                passwordError == null &&
                confirmPasswordError == null

        return state.copy(
            nameError = nameError,
            emailError = emailError,
            passwordError = passwordError,
            confirmPasswordError = confirmPasswordError,
            isRegisterEnabled = isValid
        )
    }

    fun onRegisterClicked() {

        val currentState = _uiState.value

        if (!currentState.isRegisterEnabled || currentState.isLoading) {
            return
        }

        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true) }

            kotlinx.coroutines.delay(1000)

            _uiState.update { it.copy(isLoading = false) }

            _uiEvent.send(RegisterUiEvent.RegisterSuccess)
        }
    }
}