package org.nmvasani.tictactoe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.nmvasani.tictactoe.ui.colors.Colors
import org.nmvasani.tictactoe.ui.composables.IosBackButton
import org.nmvasani.tictactoe.ui.composables.SettingItem
import org.nmvasani.tictactoe.viewmodels.SettingViewModel
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.main_logo2

@Preview
@Composable
fun SettingScreen(
    onBack: () -> Unit,
    onSave: () -> Unit,
    settingViewModel: SettingViewModel = koinInject()
) {

    var darkModeCheck by remember { mutableStateOf(false) }
    val soundCheck = settingViewModel.isPause.collectAsState()
    var player1Name by remember { mutableStateOf("Nimesh") }
    var player2Name by remember { mutableStateOf("John") }

    LaunchedEffect(Unit) {
        player1Name = settingViewModel.player1Name.value
        player2Name = settingViewModel.player2Name.value
    }

    val currentFocus = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxSize().imePadding().pointerInput(Unit) {
            detectTapGestures(onTap = {
                currentFocus.clearFocus()
                player1Name = player1Name.ifEmpty { settingViewModel.player1Name.value }
                player2Name = player2Name.ifEmpty { settingViewModel.player2Name.value }
            })
        }

    ) {
        IosBackButton {
            onBack()
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(start = 20.dp, end = 20.dp, top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Image(
                    painter = painterResource(Res.drawable.main_logo2),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(120.dp)
                        .padding(start = 80.dp, end = 80.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
            item { Spacer(modifier = Modifier.height(50.dp)) }
            item {
                SettingItem(
                    item1 = {
                        Text(
                            text = "${if (!soundCheck.value) "Disable" else "Enable"} Sound",
                            color = Color.Black
                        )
                    },
                    item2 = {
                        Switch(
                            checked = !soundCheck.value,
                            onCheckedChange = {
                                settingViewModel.setPause(!it)
                            }
                        )
                    }
                )
            }
            item {
                SettingItem(
                    item1 = {
                        Text(
                            text = "${if (darkModeCheck) "Disable" else "Enable"} Dark Mode",
                            color = Color.Black
                        )
                    },
                    item2 = {
                        Switch(
                            checked = darkModeCheck,
                            onCheckedChange = {
                                darkModeCheck = it
                            }
                        )
                    }
                )
            }
            item {
                SettingItem(
                    item1 = {
                        Text(
                            text = "Player 1 :",
                            color = Color.Black
                        )
                    },
                    item2 = {
                        TextField(
                            value = player1Name,
                            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                            onValueChange = {
                                player1Name = it
                            },
                            singleLine = true,
                            placeholder = { Text(text = "Player 1 Name") },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Colors.MattOrange,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                )
            }
            item {
                SettingItem(
                    item1 = {
                        Text(
                            text = "Player 2 :",
                            color = Color.Black
                        )
                    },
                    item2 = {
                        TextField(
                            value = player2Name,
                            textStyle = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp
                            ),

                            onValueChange = {
                                player2Name = it
                            },
                            singleLine = true,
                            placeholder = { Text(text = "Player 2 Name") },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Colors.MattOrange,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxHeight()
                        )
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(50.dp)) }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(bottom = 50.dp, start = 40.dp, end = 40.dp)
                        .fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            // onSave()
                        },

                        modifier = Modifier.width(120.dp).height(50.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.CherryRed),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp,
                            disabledElevation = 0.dp
                        )
                    ) {
                        Text("Reset", color = Color.White, fontSize = 18.sp)
                    }

                    Button(
                        onClick = {
                            settingViewModel.setPlayer1Name(player1Name)
                            settingViewModel.setPlayer2Name(player2Name)

                            onSave()
                        },

                        modifier = Modifier.width(120.dp).height(50.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.ForestGreen),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp,
                            disabledElevation = 0.dp
                        )
                    ) {
                        Text("Save", color = Color.White, fontSize = 18.sp)
                    }

                }
            }
        }
    }
}