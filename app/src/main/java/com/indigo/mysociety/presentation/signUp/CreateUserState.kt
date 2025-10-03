package com.indigo.mysociety.presentation.signUp

import com.domain.model.response.CreateUserResponse


data class CreateUserState(
    val isLoading: Boolean = false,
    val response: CreateUserResponse? = null,
    val error: String = ""
)