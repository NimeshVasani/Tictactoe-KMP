package org.nmvasani.tictactoe.ui.screens

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.nmvasani.tictactoe.ui.colors.Colors
import org.nmvasani.tictactoe.ui.composables.IosBackButton
import org.nmvasani.tictactoe.ui.composables.SettingItem

@Preview
@Composable
fun SettingScreen(modifier: Modifier = Modifier, onBack: () -> Unit, onSave: () -> Unit) {

    var darkModeCheck by remember { mutableStateOf(false) }
    var soundCheck by remember { mutableStateOf(true) }
    var player1Name by remember { mutableStateOf("Nimesh") }
    var player2Name by remember { mutableStateOf("John") }
    val currentFocus = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize().imePadding().pointerInput(Unit) {
            detectTapGestures(onTap = {
                currentFocus.clearFocus()
            })
        },
    ) {
        IosBackButton {
            onBack()
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(130.dp))
            SettingItem(
                item1 = {
                    Text(
                        text = "${if (soundCheck) "Disable" else "Enable"} Sound",
                        color = Color.Black
                    )
                },
                item2 = {
                    Switch(
                        checked = soundCheck,
                        onCheckedChange = {
                            soundCheck = it
                        }
                    )
                }
            )
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
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),

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
    }
}