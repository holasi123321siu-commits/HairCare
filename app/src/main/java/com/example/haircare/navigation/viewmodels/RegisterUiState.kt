package com.example.haircare.navigation.viewmodels

data class RegisterUiState(
    val name: String = "",
    val nameError: Int? = null,
    val email: String = "",
    val emailError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: Int? = null,
    val isLoading: Boolean = false,
    val isRegisterEnabled: Boolean = false
)