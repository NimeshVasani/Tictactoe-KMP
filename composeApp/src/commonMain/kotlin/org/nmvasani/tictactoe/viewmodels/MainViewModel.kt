package org.nmvasani.tictactoe.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.nmvasani.tictactoe.repositories.Difficulty
import org.nmvasani.tictactoe.repositories.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _board = MutableStateFlow(repository.board)
    val board: StateFlow<Array<Array<String>>> = _board

    private val _isEnable = MutableStateFlow(true)
    val isEnable: StateFlow<Boolean> = _isEnable

    val currentPlayer = mutableStateOf(repository.currentPlayer)
    val gameOver = mutableStateOf(repository.gameOver)
    val winner = mutableStateOf(repository.winner)
    private val _isReset = MutableStateFlow(false)
    val isReset: StateFlow<Boolean> = _isReset

    val winningCells = mutableStateOf(repository.winningCells)


    val difficulty = MutableStateFlow(repository.difficulty)

    fun setDifficulty(level: Difficulty) {
        repository.difficulty = level
        difficulty.value = level
    }

    fun makeMove(row: Int, col: Int) {
        if (repository.makeMove(row, col)) {
            updateState()

            if (repository.currentPlayer == "O" && !repository.gameOver) {
                viewModelScope.launch(Dispatchers.Default) {
                    _isEnable.value = false
                    delay(1000L)
                    repository.computerMove()
                    updateState()
                    _isEnable.value = true
                }
            }
        }
    }

    private fun updateState() {
        _board.value = repository.board
        currentPlayer.value = repository.currentPlayer
        winningCells.value = repository.winningCells
        gameOver.value = repository.gameOver
        winner.value = repository.winner
    }

    fun resetGame() {
        repository.resetGame()
        _isReset.value = true
        updateState()
    }
}
