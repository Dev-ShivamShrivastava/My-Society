package com.indigo.mysociety.presentation.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.api.AppResult
import com.domain.useCase.ServiceTicketListUseCase
import com.indigo.mysociety.dataStores.DataStorePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketVM @Inject constructor(
    private val serviceTicketListUseCase: ServiceTicketListUseCase,
    private val dataStorePrefs: DataStorePrefs
) : ViewModel() {

    private val _state = MutableStateFlow(TicketsState())
    val state: StateFlow<TicketsState> = _state.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()


    init {
        getServiceTicketListApi("Pending")
    }

     fun getServiceTicketListApi(status: String){
        viewModelScope.launch {
            serviceTicketListUseCase.invoke(status).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is AppResult.Success -> {
                        if (result.data.status == "Success"){
                            _state.update {
                                it.copy(isLoading = false, response = result.data)
                            }
                        }else {
                            _toastEvent.emit(result.data.message?:"")
                            _state.update {
                                it.copy(isLoading = false, error = result.data.message ?: "")
                            }
                        }
                    }

                    is AppResult.Error -> {
                        _toastEvent.emit(result.message)
                        _state.value = _state.value.copy(isLoading = false, error = result.message)
                    }
                }
            }
        }

    }






}