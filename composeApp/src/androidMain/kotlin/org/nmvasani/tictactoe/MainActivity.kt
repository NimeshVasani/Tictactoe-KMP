package org.nmvasani.tictactoe

import AudioPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import createDataStore
import org.jetbrains.compose.resources.ExperimentalResourceApi

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalResourceApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.light(0, 0),
                navigationBarStyle = SystemBarStyle.light(0, 0)
            )
            App(
                prefs = remember {
                    createDataStore(applicationContext)
                },
                audioPlayer = remember {
                    AudioPlayer(applicationContext)
                }
            )
        }
    }
}

