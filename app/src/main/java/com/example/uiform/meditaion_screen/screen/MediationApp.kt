package com.example.uiform.meditaion_screen.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.uiform.meditaion_screen.screen.AddMediationScreen
import com.example.uiform.ui.theme.UIFormTheme

@Composable
fun MediationApp(){
    UIFormTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            AddMediationScreen()
        }
    }
}

