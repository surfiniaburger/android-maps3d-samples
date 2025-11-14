package com.example.advancedmaps3dsamples.agent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.advancedmaps3dsamples.ui.theme.AdvancedMaps3DSamplesTheme

class AgentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvancedMaps3DSamplesTheme {
                AgentScreen()
            }
        }
    }
}
