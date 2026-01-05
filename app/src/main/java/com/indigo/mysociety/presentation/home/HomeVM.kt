package com.indigo.mysociety.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.api.AppResult
import com.domain.model.request.CreateServiceRequest
import com.domain.useCase.CreateServiceRequestUseCase
import com.domain.useCase.ServiceListUseCase
import com.domain.useCase.ServiceTicketListUseCase
import com.indigo.mysociety.dataStores.DataStorePrefs
import com.indigo.mysociety.utils.showToast
import com.indigo.mysociety.utils.toArrayList
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
class HomeVM @Inject constructor(
    private val createServiceRequestUseCase: CreateServiceRequestUseCase,
    private val serviceListUseCase: ServiceListUseCase,
    private val serviceTicketListUseCase: ServiceTicketListUseCase,
    private val dataStorePrefs: DataStorePrefs
) : ViewModel() {

    private val _state = MutableStateFlow(CreateServiceState())
    val state: StateFlow<CreateServiceState> = _state.asStateFlow()

    private val _homeUiEvent = MutableSharedFlow<HomeUIEvent>()
    val homeUiEvent = _homeUiEvent.asSharedFlow()

    var serviceList: MutableList<String> = mutableListOf()
    var whatsappNumber: String = ""
    var mobileNumber: String = ""

    init {
        getServiceList()
    }


    fun createServiceRequestApi(createServiceRequestRequest: CreateServiceRequest) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            createServiceRequestUseCase.invoke(createServiceRequestRequest).collect { result ->
                when (result) {
                    is AppResult.Loading -> {
                    }

                    is AppResult.Success -> {
                        if (result.data.status == "Success"){
                            _state.update { it.copy(isLoading = false, response = result.data) }
                            _homeUiEvent.emit(HomeUIEvent.ShowToast(result.data.message?:"Service request created successfully"))
                            _homeUiEvent.emit(HomeUIEvent.NavigateDetailsScreen)
                        }else {
                            _homeUiEvent.emit(HomeUIEvent.ShowToast(result.data.message?:""))
                            _state.update {
                                it.copy(isLoading = false, error = result.data.message ?: "")
                            }
                        }
                    }

                    is AppResult.Error -> {
                        _homeUiEvent.emit(HomeUIEvent.ShowToast(result.message))

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
                            _homeUiEvent.emit(HomeUIEvent.ShowToast(result.data.message?:""))

                        }
                    }

                    is AppResult.Error -> {
                        _homeUiEvent.emit(HomeUIEvent.ShowToast(result.message))
                    }
                }
            }
        }

    }






}