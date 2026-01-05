package com.domain.useCase

import com.domain.api.AppResult
import com.domain.model.request.CreateServiceRequest
import com.domain.model.request.CreateUserRequest
import com.domain.model.response.CreateServiceResponse
import com.domain.model.response.CreateUserResponse
import com.domain.repository.IAuthRepository
import com.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateServiceRequestUseCase(private val homeRepository: IHomeRepository) {
    operator fun invoke(createServiceRequestRequest: CreateServiceRequest): Flow<AppResult<CreateServiceResponse>> =
        flow {
            emit(homeRepository.createServiceRequestApi(createServiceRequestRequest))
        }
}

