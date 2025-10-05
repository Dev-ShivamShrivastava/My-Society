package com.indigo.mysociety.presentation.tickets

import com.domain.model.response.ServiceTicketListResponse


data class TicketsState(
    val isLoading: Boolean = false,
    val response: ServiceTicketListResponse? = null,
    val error: String = ""
)