package com.data.repositoryImpl

import com.data.api.ApiInterface
import com.data.api.safeApiCall
import com.domain.api.AppResult
import com.domain.model.request.CreateUserRequest
import com.domain.model.request.LoginRequest
import com.domain.model.response.CreateUserResponse
import com.domain.model.response.StandardResponse
import com.domain.repository.IAuthRepository
import javax.inject.Inject


class AuthRepository @Inject constructor(val apiInterface: ApiInterface) : IAuthRepository {

    override suspend fun loginApi(
        loginRequest: LoginRequest
    ): AppResult<StandardResponse> {
        return safeApiCall{ apiInterface.loginApi(loginRequest) }
    }

    override suspend fun createUserApi(createUserRequest: CreateUserRequest): AppResult<CreateUserResponse> {
        return safeApiCall{ apiInterface.createUserApi(createUserRequest) }
    }
}