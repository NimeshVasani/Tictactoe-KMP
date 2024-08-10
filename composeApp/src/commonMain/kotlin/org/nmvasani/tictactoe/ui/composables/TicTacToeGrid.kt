package org.nmvasani.tictactoe.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.cross
import tictactoe.composeapp.generated.resources.zero


@Composable
fun TicTacToeGrid(
    board: Array<Array<String>>,
    onCellClick: (Int, Int) -> Unit,
    isEnable: Boolean = true,
    resetTrigger: MutableState<Boolean>,
    gameOver: MutableState<Boolean>,
    winningLine: MutableState<List<Pair<Int, Int>>?> // Start and end of the winning line


) {
    val cellSize = 120.dp
    val animVal = remember { Animatable(0f) }
    val animWin = remember { Animatable(0f) }

    LaunchedEffect(resetTrigger.value) {
        println("called")
        if (resetTrigger.value) {
            animVal.snapTo(0f) // Instantly reset animation to the start
            delay(100) // Optional delay before starting the new animation
            animVal.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )
            resetTrigger.value = false // Reset the trigger after animation is set up
        }
    }
    LaunchedEffect(winningLine.value) {
        if (!resetTrigger.value) {
            animWin.snapTo(0f) // Instantly reset animation to the start
            delay(100) // Optional delay before starting the new animation
            animWin.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
            )

        }
    }

    Box(
        modifier = Modifier.size(360.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(10.dp),
                clip = true
            )
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidthPx = 2.dp.toPx()
            val cellSizePx = cellSize.toPx()

            // Draw vertical lines
            for (i in 1..2) {
                val x = i * cellSizePx
                drawLine(
                    color = Color.Black.copy(0.2f),
                    start = Offset(x, 40f),
                    end = Offset(x, (animVal.value * size.height) - 40f),
                    strokeWidth = strokeWidthPx
                )
            }
            // Draw horizontal lines
            for (i in 1..2) {
                val y = i * cellSizePx
                drawLine(
                    color = Color.Black.copy(0.2f),
                    start = Offset(40f, y),
                    end = Offset((animVal.value * size.width) - 40f, y),
                    strokeWidth = strokeWidthPx
                )
            }


        }


        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            for (row in 0..2) {
                Row {
                    for (col in 0..2) {
                        Box(
                            modifier = Modifier
                                .size(cellSize)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = if (isEnable && !resetTrigger.value) rememberRipple(
                                        bounded = true,
                                        radius = 50.dp,
                                        color = Color.Green
                                    ) else null,
                                    onClick = {
                                        if (isEnable && !resetTrigger.value)
                                            onCellClick(row, col)
                                    },
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (board[row][col].isNotEmpty()) {
                                Image(
                                    painter = if (board[row][col] == "X") painterResource(Res.drawable.cross)
                                    else painterResource(Res.drawable.zero),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize().padding(15.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidthPx = 2.dp.toPx()
            val cellSizePx = cellSize.toPx()

            // Draw the winning line if the game is over and there's a winning line
            if (gameOver.value && winningLine.value != null) {
                val (start, _, end) = winningLine.value!!

                val startX = start.second * cellSizePx + cellSizePx / 2
                val startY = start.first * cellSizePx + cellSizePx / 2
                val endX = end.second * cellSizePx + cellSizePx / 2
                val endY = end.first * cellSizePx + cellSizePx / 2

                drawLine(
                    color = Color.Red,
                    start = Offset(startX, startY),
                    end = Offset(animWin.value * endX, animWin.value * endY),
                    strokeWidth = strokeWidthPx
                )
            }
        }
    }
}
