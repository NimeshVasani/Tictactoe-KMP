package org.nmvasani.tictactoe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.nmvasani.tictactoe.ui.colors.Colors
import org.nmvasani.tictactoe.ui.composables.CustomRadioButton
import org.nmvasani.tictactoe.ui.composables.DifficultySelection
import org.nmvasani.tictactoe.ui.composables.IosBackButton
import org.nmvasani.tictactoe.viewmodels.SinglePlayerViewModel
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.cross
import tictactoe.composeapp.generated.resources.zero

@Composable
fun SelectionScreen(
    onBack: () -> Unit,
    onNavigateToSinglePlayer: () -> Unit,
    viewModel: SinglePlayerViewModel = koinInject()
) {
    var selected by remember { mutableStateOf(1) }
    val userSelected by viewModel.userSelected.collectAsState()

    LaunchedEffect(Unit) {
        selected = if (userSelected == "X") 1 else 2
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        IosBackButton {
            onBack()
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Pick your side",
                color = Colors.EgyptianBlue,
                fontSize = 22.sp,
                fontWeight = W600
            )

            Spacer(modifier = Modifier.height(100.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth().height(300.dp)
                    .padding(start = 50.dp, end = 50.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f).fillMaxHeight().clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        selected = 1
                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(Res.drawable.cross),
                        contentDescription = "selection cross",
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        alpha = if (selected == 1) 1f else 0.3f
                    )
                    CustomRadioButton(
                        selected = selected == 1,
                        onClick = { selected = 1 },
                        selectedColor = Colors.SkyBlue,
                        unselectedColor = Colors.SkyBlue.copy(0.5f),
                        size = 30
                    )

                }
                Spacer(modifier = Modifier.weight(0.2f))
                Column(
                    modifier = Modifier.weight(1f).fillMaxHeight().clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        selected = 2
                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(Res.drawable.zero),
                        contentDescription = "selection cross",
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        alpha = if (selected == 2) 1f else 0.3f

                    )
                    CustomRadioButton(
                        selected = selected == 2,
                        onClick = { selected = 2 },
                        selectedColor = Colors.SkyBlue,
                        unselectedColor = Colors.SkyBlue.copy(0.5f),
                        size = 30
                    )


                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            DifficultySelection(viewModel = viewModel)
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    viewModel.setCurrentPlayer(if (selected == 1) "X" else "O")
                    println(viewModel.userSelected.value)
                    onNavigateToSinglePlayer()
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
                Text("Continue", color = Color.Black, fontSize = 16.sp)
            }

        }
    }
}