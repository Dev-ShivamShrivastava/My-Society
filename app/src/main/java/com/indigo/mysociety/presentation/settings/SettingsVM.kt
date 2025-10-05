package com.indigo.mysociety.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.domain.api.AppResult
import com.domain.model.response.LoginResponse
import com.domain.useCase.ServiceTicketListUseCase
import com.google.gson.Gson
import com.indigo.mysociety.dataStores.DataStorePrefs
import com.indigo.mysociety.dataStores.PreferenceKeys
import com.indigo.mysociety.presentation.tickets.TicketsState
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
class SettingsVM @Inject constructor(
    private val dataStorePrefs: DataStorePrefs
) : ViewModel() {

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    var userInfo by mutableStateOf<LoginResponse.UserInfoData?>(null)
        private set



    init {
        viewModelScope.launch {
            dataStorePrefs.getPreference(PreferenceKeys.KEY_USER_INFO, "").collect {
                userInfo = Gson().fromJson(it, LoginResponse.UserInfoData::class.java)
            }

        }
    }

    fun clearAllInfo(){
        viewModelScope.launch {
            dataStorePrefs.clearAllPreference()
        }
    }

}