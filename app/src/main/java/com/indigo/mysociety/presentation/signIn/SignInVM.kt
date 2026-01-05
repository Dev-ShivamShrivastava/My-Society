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
import com.google.gson.Gson
import com.indigo.mysociety.dataStores.DataStorePrefs
import com.indigo.mysociety.dataStores.PreferenceKeys
import com.indigo.mysociety.presentation.signUp.CreateUserState
import com.indigo.mysociety.utils.isValidEmail
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


    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onLoginClicked(email: String, password: String) {
        viewModelScope.launch {
            when {
                email.isBlank() -> _uiEvent.emit(LoginUiEvent.ShowToast("Email is required" ))
                !isValidEmail(email) -> _uiEvent.emit(LoginUiEvent.ShowToast("Invalid email" ))
                password.isBlank() -> _uiEvent.emit(LoginUiEvent.ShowToast("Password is required" ))
                else -> loginApi(LoginRequest(email, password))
            }
        }
    }

    fun loginApi(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            loginUseCase.invoke(loginRequest).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                    }
                    is AppResult.Success -> {
                        if (result.data.status == "Success"){
                            dataStorePrefs.putPreference(PreferenceKeys.KEY_AUTH_TOKEN, result.data.data?.token?:"")
                            dataStorePrefs.putPreference(PreferenceKeys.KEY_USER_INFO, Gson().toJson(result.data.data))
                            _state.update { it.copy(isLoading = false, response = result.data) }
                            _uiEvent.emit(LoginUiEvent.NavigateHome)
                        }else {
                            _state.value = _state.value.copy(isLoading = false)
                            _uiEvent.emit(LoginUiEvent.ShowToast(result.data.message ?: "Error"))
                        }
                    }

                    is AppResult.Error -> {
                        _state.value = _state.value.copy(isLoading = false, error = result.message)
                        _uiEvent.emit(LoginUiEvent.ShowToast(result.message))
                    }
                }
            }
        }

    }

}