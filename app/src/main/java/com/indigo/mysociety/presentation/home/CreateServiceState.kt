package com.indigo.mysociety.presentation.home

import com.domain.model.response.CreateServiceResponse


data class CreateServiceState(
    val isLoading: Boolean = false,
    val response: CreateServiceResponse? = null,
    val error: String = ""
)