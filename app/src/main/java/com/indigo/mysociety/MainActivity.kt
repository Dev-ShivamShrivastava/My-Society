package com.indigo.mysociety

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.indigo.mysociety.navigations.AppNavigation
import com.indigo.mysociety.ui.theme.MySocietyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MySocietyTheme {
                AppNavigation()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    AppNavigation()
}