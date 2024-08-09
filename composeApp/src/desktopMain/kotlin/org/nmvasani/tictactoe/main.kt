package org.nmvasani.tictactoe

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tic tac toe",
    ) {
        App()
    }
}