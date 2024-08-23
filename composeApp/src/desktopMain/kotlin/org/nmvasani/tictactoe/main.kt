package org.nmvasani.tictactoe

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import createDataStore
import dataStoreFileName

fun main() {
    val prefs = createDataStore { dataStoreFileName }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Tic tac toe",
        ) {
            App(
                prefs = prefs
            )
        }
    }
}