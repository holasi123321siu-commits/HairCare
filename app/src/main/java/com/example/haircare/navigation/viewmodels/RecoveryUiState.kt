package com.example.haircare.navigation.viewmodels

data class RecoveryUiState(
    val email: String = "",
    val emailError: Int? = null,
    val isLoading: Boolean = false,
    val isRecoveryEnabled: Boolean = false
)