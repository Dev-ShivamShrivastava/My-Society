package com.indigo.mysociety.presentation.signUp

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.api.AppResult
import com.domain.model.request.CreateUserRequest
import com.domain.useCase.CreateUserUseCase
import com.domain.useCase.LoginUseCase
import com.indigo.mysociety.dataStores.DataStorePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpVM @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val dataStorePrefs: DataStorePrefs
) : ViewModel() {

    private val _state = MutableStateFlow(CreateUserState())
    val state: StateFlow<CreateUserState> = _state.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()


    fun createUserApi(createUserRequest: CreateUserRequest) {
        viewModelScope.launch {
            createUserUseCase.invoke(createUserRequest).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is AppResult.Success -> {
                        _state.update { it.copy(isLoading = false, response = result.data) }
                    }

                    is AppResult.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message)
                        _toastEvent.emit(result.message)
                    }
                }
            }
        }

    }

}