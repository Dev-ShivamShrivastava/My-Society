package com.domain.repository

import com.domain.model.request.LoginRequest
import com.domain.model.response.StandardResponse
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    suspend fun login(loginRequest: LoginRequest): Flow<StandardResponse>

}