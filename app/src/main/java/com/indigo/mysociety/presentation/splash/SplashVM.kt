package com.indigo.mysociety.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indigo.mysociety.dataStores.DataStorePrefs
import com.indigo.mysociety.dataStores.PreferenceKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(
    private val dataStorePrefs: DataStorePrefs
) : ViewModel() {



    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    var token=""

    init {
        viewModelScope.launch {
            dataStorePrefs.getPreference(PreferenceKeys.KEY_AUTH_TOKEN,"").collect {
                token = it
            }
        }
    }

}