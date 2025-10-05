package com.indigo.mysociety.presentation.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.indigo.mysociety.utils.Dimens

@Composable
fun SettingsScreen(){
    Text("Settings Screen", modifier = Modifier.padding(Dimens._16dp).fillMaxSize(),)
}