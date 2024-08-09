package org.nmvasani.tictactoe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform