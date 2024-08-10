package org.nmvasani.tictactoe.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.nmvasani.tictactoe.ui.composables.TicTacToeGrid
import org.nmvasani.tictactoe.viewmodels.MainViewModel

@Composable
fun PlayingScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = koinInject()) {
    val board by viewModel.board.collectAsState()
    val currentPlayer by remember { viewModel.currentPlayer }
    val gameOver = remember { viewModel.gameOver }
    val winner by remember { viewModel.winner }
    val isEnable by viewModel.isEnable.collectAsState()
    val resetTrigger = remember { mutableStateOf(true) }
    val winningLine = remember { viewModel.winningCells }




    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TicTacToeGrid(board = board, onCellClick = { row, col ->
            viewModel.makeMove(row, col)
        }, isEnable = isEnable, resetTrigger = resetTrigger, gameOver = gameOver,winningLine=winningLine)

        Spacer(modifier = Modifier.height(16.dp))

        if (gameOver.value) {
            Text(
                text = if (winner != null) "Winner: $winner" else "Draw",
                color = Color.White,
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                viewModel.resetGame()
                resetTrigger.value = true
            }) {
                Text("Play Again")
            }
        } else {
            Text(
                text = "Current Player: $currentPlayer",
                color = Color.White,
                style = MaterialTheme.typography.h4
            )
        }
    }
}

