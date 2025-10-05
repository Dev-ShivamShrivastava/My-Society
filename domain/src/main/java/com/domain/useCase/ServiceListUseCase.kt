package com.domain.useCase

import com.domain.api.AppResult
import com.domain.model.request.CreateServiceRequest
import com.domain.model.request.CreateUserRequest
import com.domain.model.response.CreateServiceResponse
import com.domain.model.response.CreateUserResponse
import com.domain.model.response.ServiceListResponse
import com.domain.repository.IAuthRepository
import com.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServiceListUseCase(private val homeRepository: IHomeRepository) {
    operator fun invoke(): Flow<AppResult<ServiceListResponse>> =
        flow {
            emit(homeRepository.getServiceListApi())
        }
}

