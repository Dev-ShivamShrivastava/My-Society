package com.indigo.mysociety.presentation.tickets

sealed class TicketUIEvent {
    data class ShowToast(val message: String): TicketUIEvent()
}