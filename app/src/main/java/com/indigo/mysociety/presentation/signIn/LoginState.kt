package com.indigo.mysociety.presentation.signIn

import com.domain.model.response.LoginResponse


data class LoginState(
    val isLoading: Boolean = false,
    val response: LoginResponse? = null,
    val error: String = ""
)