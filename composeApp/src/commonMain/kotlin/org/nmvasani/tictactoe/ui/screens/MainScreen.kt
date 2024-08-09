package org.nmvasani.tictactoe.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.nmvasani.tictactoe.viewmodels.MainViewModel
import org.nmvasani.tictactoe.repositories.Difficulty
import org.nmvasani.tictactoe.ui.colors.Colors
import org.nmvasani.tictactoe.ui.composables.RotatingIcon
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.mainlogo

@Composable
fun MainScreen(
    onNavigateToSinglePlayer: () -> Unit,
    onNavigateToMultiPlayer: () -> Unit,
    viewModel: MainViewModel = koinInject()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(Res.drawable.mainlogo),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(150.dp).padding(start = 50.dp, end = 50.dp),
            contentScale = ContentScale.FillBounds
        )


        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Choose your play mode",
            color = Color.Blue.copy(0.82f),
            fontSize = 22.sp,
            fontWeight = W600
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                onNavigateToSinglePlayer()
            },
            modifier = Modifier.width(150.dp).height(50.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.SkyBlue),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 15.dp,
                disabledElevation = 0.dp
            )
        ) {
            Text("With AI", color = Color.White, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onNavigateToMultiPlayer()
            },
            modifier = Modifier.width(150.dp).height(50.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 15.dp,
                disabledElevation = 0.dp
            )
        ) {
            Text("With a friend", color = Color.Black, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(50.dp))

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

@Composable
fun DifficultySelection(viewModel: MainViewModel) {
    val difficulties = Difficulty.entries.toTypedArray()
    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
        difficulties.forEach { level ->
            Button(
                onClick = { viewModel.setDifficulty(level) },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(level.name, color = Color.White)
            }
        }
    }
}

