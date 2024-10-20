package com.exhibitiondot.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.exhibitiondot.presentation.ui.DoTApp
import com.exhibitiondot.presentation.ui.navigation.ScreenGraph
import com.exhibitiondot.presentation.ui.rememberDoTAppState
import com.exhibitiondot.presentation.ui.theme.DoTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberDoTAppState()
            DoTTheme {
                DoTApp(
                    appState = appState,
                    startDestination = ScreenGraph.SignGraph
                )
            }
        }
    }
}