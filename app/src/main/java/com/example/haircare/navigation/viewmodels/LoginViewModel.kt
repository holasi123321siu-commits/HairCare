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

class LoginViewModel : ViewModel() {

    // Estado de la pantalla
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Eventos de la pantalla
    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEmailChanged(email: String) {
        _uiState.update { currentState ->
            val emailError = validateEmail(email)

            currentState.copy(
                email = email,
                emailError = emailError,
                isLoginEnabled = isFormValid(
                    email,
                    currentState.password,
                    emailError,
                    currentState.passwordError
                )
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { currentState ->
            val passwordError = validatePassword(password)

            currentState.copy(
                password = password,
                passwordError = passwordError,
                isLoginEnabled = isFormValid(
                    currentState.email,
                    password,
                    currentState.emailError,
                    passwordError
                )
            )
        }
    }

    fun onLoginClicked() {

        val currentState = _uiState.value

        if (!currentState.isLoginEnabled || currentState.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val isSuccess = simulateApiCall(
                currentState.email,
                currentState.password
            )

            _uiState.update { it.copy(isLoading = false) }

            if (isSuccess) {
                _uiEvent.send(LoginUiEvent.NavigateToHome)
            } else {
                _uiEvent.send(
                    LoginUiEvent.ShowError(R.string.error_login)
                )
            }
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

    // Comprueba si todos los campos son correctos
    private fun isFormValid(
        email: String,
        password: String,
        emailError: Int?,
        passwordError: Int?
    ): Boolean {
        return email.isNotBlank() &&
                password.isNotBlank() &&
                emailError == null &&
                passwordError == null
    }

    // Simulación del inicio de sesión
    private suspend fun simulateApiCall(
        email: String,
        password: String
    ): Boolean {
        kotlinx.coroutines.delay(1500)
        return email == "admin@test.com" && password == "12345678"
    }
}