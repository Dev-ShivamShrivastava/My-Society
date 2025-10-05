package com.indigo.mysociety.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.api.AppResult
import com.domain.model.request.CreateServiceRequest
import com.domain.useCase.CreateServiceRequestUseCase
import com.domain.useCase.ServiceListUseCase
import com.domain.useCase.ServiceTicketListUseCase
import com.indigo.mysociety.dataStores.DataStorePrefs
import com.indigo.mysociety.utils.toArrayList
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
class HomeVM @Inject constructor(
    private val createServiceRequestUseCase: CreateServiceRequestUseCase,
    private val serviceListUseCase: ServiceListUseCase,
    private val serviceTicketListUseCase: ServiceTicketListUseCase,
    private val dataStorePrefs: DataStorePrefs
) : ViewModel() {

    private val _state = MutableStateFlow(CreateServiceState())
    val state: StateFlow<CreateServiceState> = _state.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    var serviceList: MutableList<String> = mutableListOf()
    var whatsappNumber: String = ""
    var mobileNumber: String = ""

    init {
        getServiceList()
    }


    fun createServiceRequestApi(createServiceRequestRequest: CreateServiceRequest) {
        viewModelScope.launch {
            createServiceRequestUseCase.invoke(createServiceRequestRequest).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is AppResult.Success -> {
                        if (result.data.status == "Success"){
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


    private fun getServiceList(){
        viewModelScope.launch {
            serviceListUseCase.invoke().collect { result ->
                when (result) {
                    is AppResult.Loading -> {

                    }

                    is AppResult.Success -> {
                        if (result.data.status == "Success"){
                            serviceList = result.data.data?.services.toArrayList()
                            whatsappNumber = result.data.data?.whatsappNo ?:""
                            mobileNumber = result.data.data?.mobileNo ?:""
                        }else {
                            _toastEvent.emit(result.data.message?:"")
                        }
                    }

                    is AppResult.Error -> {

                        _toastEvent.emit(result.message)
                    }
                }
            }
        }

    }






}