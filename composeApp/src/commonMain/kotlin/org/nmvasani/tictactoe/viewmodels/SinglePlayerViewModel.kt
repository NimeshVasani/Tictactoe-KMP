package org.nmvasani.tictactoe.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Single
import org.nmvasani.tictactoe.repositories.Difficulty
import org.nmvasani.tictactoe.repositories.SinglePlayerRepository

@Single
@KoinViewModel
class SinglePlayerViewModel(private val repository: SinglePlayerRepository) : ViewModel() {

    private val _board = MutableStateFlow(repository.board)
    val board: StateFlow<Array<Array<String>>> = _board

    private val _isEnable = MutableStateFlow(true)
    val isEnable: StateFlow<Boolean> = _isEnable

    val currentPlayer = mutableStateOf(repository.currentPlayer)
    val gameOver = mutableStateOf(repository.gameOver)
    val winner = mutableStateOf(repository.winner)
    private val _isReset = MutableStateFlow(false)

    private val _score = MutableStateFlow(Pair(0, 0))
    val score = _score.asStateFlow()

    private val _userSelected: MutableStateFlow<String> = MutableStateFlow("X")
    val userSelected = _userSelected.asStateFlow()

    val winningCells = mutableStateOf(repository.winningCells)


    val difficulty = MutableStateFlow(repository.difficulty)

    fun setDifficulty(level: Difficulty) {
        repository.difficulty = level
        difficulty.value = level
    }

    fun makeMove(row: Int, col: Int) {
        if (repository.makeMove(row, col)) {
            updateState()

            if (repository.currentPlayer != "X" && !repository.gameOver) {
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
        gameOver.value = repository.gameOver
        winner.value = repository.winner
        winningCells.value = repository.winningCells
    }

    fun resetGame() {
        repository.resetGame()
        _isReset.value = true
        updateState()
    }

    fun setCurrentPlayer(who: String) {
        _userSelected.value = who
    }

    fun updateScore(winner:String) {
        if (winner == "X") {
            _score.value = Pair(score.value.first+1,score.value.second)

        } else if (winner== "O") {
            _score.value = Pair(score.value.first,score.value.second+1)
        }
    }
}
