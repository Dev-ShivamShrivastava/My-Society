package com.indigo.mysociety.presentation.signIn

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.api.AppResult
import com.domain.model.request.CreateUserRequest
import com.domain.model.request.LoginRequest
import com.domain.useCase.CreateUserUseCase
import com.domain.useCase.LoginUseCase
import com.indigo.mysociety.dataStores.DataStorePrefs
import com.indigo.mysociety.dataStores.PreferenceKeys
import com.indigo.mysociety.presentation.signUp.CreateUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SignInVM @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val dataStorePrefs: DataStorePrefs
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()


    fun loginApi(loginRequest: LoginRequest) {
        viewModelScope.launch {
            loginUseCase.invoke(loginRequest).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is AppResult.Success -> {
                        if (result.data.status == "Success"){
                            dataStorePrefs.putPreference(PreferenceKeys.KEY_AUTH_TOKEN, result.data.data?.token?:"")
                            _state.update { it.copy(isLoading = false, response = result.data) }
                        }else {
                            _toastEvent.emit(result.data.message?:"")
                            _state.update {
                                it.copy(isLoading = false, error = result.data.message ?: "")
                            }
                        }
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