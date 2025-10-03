package com.indigo.mysociety.presentation.home

import androidx.lifecycle.ViewModel
import com.domain.useCase.CreateUserUseCase
import com.domain.useCase.LoginUseCase
import com.indigo.mysociety.dataStores.DataStorePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val dataStorePrefs: DataStorePrefs
) : ViewModel() {


    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()




}