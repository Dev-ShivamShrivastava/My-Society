package com.domain.useCase

import com.domain.api.AppResult
import com.domain.model.request.LoginRequest
import com.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginUseCase(private val authRepository: IAuthRepository) {
    suspend operator fun invoke(loginRequest: LoginRequest) = authRepository.loginApi(loginRequest)
}

