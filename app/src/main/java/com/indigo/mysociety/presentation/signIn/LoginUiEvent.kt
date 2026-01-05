package com.indigo.mysociety.presentation.signIn

sealed class LoginUiEvent {
    data class ShowToast(val message: String) : LoginUiEvent()
    object NavigateHome : LoginUiEvent()
}