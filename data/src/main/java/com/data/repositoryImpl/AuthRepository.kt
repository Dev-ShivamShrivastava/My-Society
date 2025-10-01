package com.data.repositoryImpl

import com.data.api.ApiInterface
import com.domain.model.request.LoginRequest
import com.domain.model.response.StandardResponse
import com.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(val apiInterface: ApiInterface) : IAuthRepository {

    override suspend fun login(
        loginRequest: LoginRequest
    ): Flow<StandardResponse> {
        val response =apiInterface.loginApi(loginRequest)
        return flow {
            emit(response)
        }

    }
}