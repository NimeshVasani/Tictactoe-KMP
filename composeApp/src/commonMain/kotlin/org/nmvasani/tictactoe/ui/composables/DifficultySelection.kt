package org.nmvasani.tictactoe.ui.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import chaintech.videoplayer.model.AudioFile
import chaintech.videoplayer.model.AudioPlayerConfig
import chaintech.videoplayer.ui.audio.AudioPlayerView
import org.nmvasani.tictactoe.repositories.Difficulty
import org.nmvasani.tictactoe.ui.colors.Colors
import org.nmvasani.tictactoe.viewmodels.SinglePlayerViewModel

@Composable
fun DifficultySelection(viewModel: SinglePlayerViewModel) {
    val difficulties = Difficulty.entries.toTypedArray()
    val selectedDifficulty = viewModel.difficulty.collectAsState()


    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.height(40.dp)
            .shadow(elevation = 5.dp)

    ) {
        difficulties.forEach { level ->
            Button(
                onClick = {

                    viewModel.setDifficulty(level)
                },

                colors = ButtonDefaults.buttonColors(backgroundColor = if (selectedDifficulty.value == level) Colors.MattOrange else Colors.SurfaceWhite),
                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxHeight(),
                interactionSource = remember { MutableInteractionSource() },


                ) {
                Text(
                    level.name,
                    color = if (selectedDifficulty.value == level) Color.White else Color.Black
                )
            }
        }
    }
}


