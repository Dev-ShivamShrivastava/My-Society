package com.domain.useCase

import com.domain.api.AppResult
import com.domain.model.request.CreateUserRequest
import com.domain.model.response.CreateUserResponse
import com.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateUserUseCase(private val authRepository: IAuthRepository) {
    operator fun invoke(createUserRequest: CreateUserRequest): Flow<AppResult<CreateUserResponse>> =
        flow {
            emit(AppResult.Loading)
            emit(authRepository.createUserApi(createUserRequest))
        }
}

