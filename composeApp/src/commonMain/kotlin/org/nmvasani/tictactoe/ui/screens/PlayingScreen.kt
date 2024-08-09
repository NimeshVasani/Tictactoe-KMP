package org.nmvasani.tictactoe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.nmvasani.tictactoe.viewmodels.MainViewModel
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.cross
import tictactoe.composeapp.generated.resources.zero

@Composable
fun PlayingScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = koinInject()) {
    val board by viewModel.board.collectAsState()
    val currentPlayer by remember { viewModel.currentPlayer }
    val gameOver by remember { viewModel.gameOver }
    val winner by remember { viewModel.winner }
    val isEnable by viewModel.isEnable.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TicTacToeGrid(board = board, onCellClick = { row, col ->
            viewModel.makeMove(row, col)
        }, isEnable = isEnable)

        Spacer(modifier = Modifier.height(16.dp))

        if (gameOver) {
            Text(
                text = if (winner != null) "Winner: $winner" else "Draw",
                color = Color.White,
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.resetGame() }) {
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


@Composable
fun TicTacToeGrid(
    board: Array<Array<String>>,
    onCellClick: (Int, Int) -> Unit,
    isEnable: Boolean = true
) {
    val cellSize = 100.dp
    Column {
        for (row in 0..2) {
            Row(modifier = Modifier.bottomBorder(2.dp, Color.White)) {
                for (col in 0..2) {
                    Box(
                        modifier = Modifier
                            .size(cellSize)
                            .background(Color.Gray)
                            .clickable { if (isEnable) onCellClick(row, col) }
                            .bottomBorder(2.dp, if (row == 2) Color.Transparent else Color.White)
                            .sideBorder(2.dp, if (col == 2) Color.Transparent else Color.White)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center,

                        ) {
                        if (board[row][col].isNotEmpty()) {
                            Image(
                                painter = if (board[row][col] == "X") painterResource(Res.drawable.cross)
                                else painterResource(Res.drawable.zero),
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
    }

}

fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

fun Modifier.sideBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width - strokeWidthPx / 2
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = width, y = 0f),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)
