package com.example.uiform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.uiform.meditaion_screen.screen.MediationApp
import com.example.uiform.ui.theme.UIFormTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UIFormTheme {
                MediationApp()
            }
        }
    }
}
