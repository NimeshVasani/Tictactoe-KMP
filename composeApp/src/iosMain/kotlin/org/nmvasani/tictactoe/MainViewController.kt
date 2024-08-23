package org.nmvasani.tictactoe

import AudioPlayer
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import createDataStore

fun MainViewController() = ComposeUIViewController {
    App(
        prefs = remember { createDataStore() },
        audioPlayer = remember { AudioPlayer() }
    )
}