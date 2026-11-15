package com.example.haircare.navigation.viewmodels

sealed interface RecoveryUiEvent {
    object RecoverySuccess : RecoveryUiEvent
    data class ShowError(val messageResId: Int) : RecoveryUiEvent
}