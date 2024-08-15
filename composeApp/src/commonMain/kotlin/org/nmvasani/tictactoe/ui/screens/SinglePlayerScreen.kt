package org.nmvasani.tictactoe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import org.nmvasani.tictactoe.ui.colors.Colors
import org.nmvasani.tictactoe.ui.composables.CustomDialog
import org.nmvasani.tictactoe.ui.composables.IosBackButton
import org.nmvasani.tictactoe.ui.composables.RotatingIcon
import org.nmvasani.tictactoe.ui.composables.TicTacToeGrid
import org.nmvasani.tictactoe.viewmodels.SinglePlayerViewModel

@Composable
fun SinglePlayerScreen(
    modifier: Modifier = Modifier,
    viewModel: SinglePlayerViewModel = koinInject(),
    onBack: () -> Unit
) {
    val board by viewModel.board.collectAsState()
    val currentPlayer by remember { viewModel.currentPlayer }
    val gameOver = remember { viewModel.gameOver }
    val winner by remember { viewModel.winner }
    val isEnable by viewModel.isEnable.collectAsState()
    val resetTrigger = remember { mutableStateOf(true) }
    val winningLine = remember { viewModel.winningCells }
    val userSelected = viewModel.userSelected
    val openAlertDialog = remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        IosBackButton {
            onBack()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(150.dp))
            if (gameOver.value) {
                Text(
                    text = if (winner != null) "Winner: ${if (winner == "X") "You" else "AI"}" else "Draw",
                    color = Colors.ForestGreen,
                    fontWeight = FontWeight.W600,
                    fontSize = 24.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                )
            } else {
                Text(
                    text = "Current Player: ${if (currentPlayer == "X") "You" else "AI"}",
                    color = if (currentPlayer == "X") Colors.ForestGreen else Colors.CherryRed,
                    fontWeight = FontWeight.W600,
                    fontSize = 24.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
            ) {
                Text(
                    text = "Alex",
                    color = Colors.EgyptianBlue,
                    fontWeight = FontWeight.W600,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(80.dp)
                )
                Box(
                    modifier = Modifier.width(100.dp)
                        .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp))
                        .background(Color.White)
                ) {
                    Text(
                        text = "1-2",
                        color = Colors.EgyptianBlue,
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 25.dp, vertical = 10.dp)
                            .align(Alignment.Center)
                    )
                }

                Text(
                    text = "AI",
                    color = Colors.EgyptianBlue,
                    fontWeight = FontWeight.W600,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(80.dp)

                )

            }
            Spacer(modifier = Modifier.height(30.dp))
            TicTacToeGrid(
                board = board,
                onCellClick = { row, col ->
                    viewModel.makeMove(row, col)
                },
                isEnable = isEnable,
                resetTrigger = resetTrigger,
                gameOver = gameOver,
                winningLine = winningLine,
                userSelected = userSelected
            )

            when {
                openAlertDialog.value -> {
                    CustomDialog(
                        onDismissRequest = { openAlertDialog.value = false },
                        onConfirmation = {
                            viewModel.resetGame()
                            resetTrigger.value = true
                            openAlertDialog.value = false
                        },
                        dialogTitle = "Restart game?",
                        dialogText = "It will erase all your moves and start a new game.",
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    if (gameOver.value) {
                        viewModel.resetGame()
                        resetTrigger.value = true
                    } else {
                        openAlertDialog.value = true
                    }
                },

                modifier = Modifier.width(150.dp).height(50.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Colors.ForestGreen,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 15.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(
                    if (gameOver.value) "Play Again" else "Restart",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            RotatingIcon(
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = null,
                        tint = Colors.SkyBlue,
                        modifier = Modifier.fillMaxSize().padding(0.dp),
                    )
                },
                mainSize = 60.dp
            )
        }
    }
}

