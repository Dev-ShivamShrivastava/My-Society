package com.indigo.mysociety.di

import com.data.repositoryImpl.AuthRepository
import com.data.repositoryImpl.HomeRepository
import com.domain.useCase.CreateServiceRequestUseCase
import com.domain.useCase.CreateUserUseCase
import com.domain.useCase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun loginUseCase(authRepository: AuthRepository) = LoginUseCase(authRepository)

    @Provides
    fun createUserUseCase(authRepository: AuthRepository) = CreateUserUseCase(authRepository)

    @Provides
    fun createServiceRequestUseCase(homeRepository: HomeRepository) =
        CreateServiceRequestUseCase(homeRepository)

}