package org.nmvasani.tictactoe.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Single
import org.nmvasani.tictactoe.repositories.MultiplayerRepository

@Single
@KoinViewModel
class MultiplayerViewModel(private val repository: MultiplayerRepository) : ViewModel() {

    private val _board = MutableStateFlow(repository.board)
    val board: StateFlow<Array<Array<String>>> = _board

    val currentPlayer = mutableStateOf(repository.currentPlayer)
    val gameOver = mutableStateOf(repository.gameOver)
    val winner = mutableStateOf(repository.winner)
    val winningCells = mutableStateOf(repository.winningCells)

    fun makeMove(row: Int, col: Int) {
        if (repository.makeMove(row, col)) {
            updateState()
        }
    }

    private fun updateState() {
        _board.value = repository.board
        currentPlayer.value = repository.currentPlayer
        gameOver.value = repository.gameOver
        winner.value = repository.winner
        winningCells.value = repository.winningCells
    }

    fun resetGame() {
        repository.resetGame()
        updateState()
    }
}
