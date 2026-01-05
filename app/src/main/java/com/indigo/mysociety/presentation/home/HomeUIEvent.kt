package com.indigo.mysociety.presentation.home

sealed class HomeUIEvent {
    data class ShowToast(val message: String): HomeUIEvent()
    object NavigateDetailsScreen: HomeUIEvent()
}