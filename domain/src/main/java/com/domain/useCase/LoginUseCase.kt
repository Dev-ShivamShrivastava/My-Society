package com.domain.useCase

import com.domain.api.AppResult
import com.domain.model.request.LoginRequest
import com.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.flow

class LoginUseCase(private val authRepository: IAuthRepository) {
    operator fun invoke(loginRequest: LoginRequest) = flow {
        emit(authRepository.loginApi(loginRequest))
    }
}

