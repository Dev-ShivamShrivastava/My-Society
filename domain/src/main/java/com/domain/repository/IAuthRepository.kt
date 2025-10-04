package com.domain.repository

import com.domain.model.request.LoginRequest
import com.domain.model.response.StandardResponse
import com.domain.api.AppResult
import com.domain.model.request.CreateUserRequest
import com.domain.model.response.CreateUserResponse
import com.domain.model.response.LoginResponse

interface IAuthRepository {

    suspend fun loginApi(loginRequest: LoginRequest): AppResult<LoginResponse>
    suspend fun createUserApi(createUserRequest: CreateUserRequest): AppResult<CreateUserResponse>

}