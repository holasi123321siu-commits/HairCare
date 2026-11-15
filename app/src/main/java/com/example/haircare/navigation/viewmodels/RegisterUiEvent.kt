package com.example.haircare.navigation.viewmodels

sealed interface RegisterUiEvent {
    object RegisterSuccess : RegisterUiEvent
    data class ShowError(val messageResId: Int) : RegisterUiEvent
}