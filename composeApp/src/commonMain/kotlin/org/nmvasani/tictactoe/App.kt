package org.nmvasani.tictactoe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import chaintech.videoplayer.model.AudioFile
import chaintech.videoplayer.util.CMPAudioPlayer
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.nmvasani.tictactoe.di.viewModelModule
import org.nmvasani.tictactoe.ui.colors.Colors
import org.nmvasani.tictactoe.ui.screens.MainScreen
import org.nmvasani.tictactoe.ui.screens.MultiplayerScreen
import org.nmvasani.tictactoe.ui.screens.SelectionScreen
import org.nmvasani.tictactoe.ui.screens.SettingScreen
import org.nmvasani.tictactoe.ui.screens.SinglePlayerScreen
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.main_back_music

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(viewModelModule)
    }) {
        MaterialTheme {
            val lifecycleOwner = LocalLifecycleOwner.current
            var isPause by remember { mutableStateOf(false) } // State for pausing/resuming audio
            var totalTime by remember { mutableStateOf(0) } // Total duration of the audio
            var currentTime by remember { mutableStateOf(0) } // Current playback time
            var isSliding by remember { mutableStateOf(false) } // Flag indicating if the seek bar is being slid
            var sliderTime: Int? by remember { mutableStateOf(null) } // Time indicated by the seek bar
            var isLoading by remember { mutableStateOf(true) } // Flag indicating audio buffer
            var currentIndex by remember { mutableStateOf(0) }
            var isShuffle by remember { mutableStateOf(false) } // State for Shuffle
            var isRepeat by remember { mutableStateOf(true) } // State for repeat one
            val navController = rememberNavController()
            val audioFilesArray = listOf(
                AudioFile(
                    audioUrl = "https://codeskulptor-demos.commondatastorage.googleapis.com/GalaxyInvaders/theme_01.mp3",
                    audioTitle = "Galaxy Invaders",
                    thumbnailUrl = "https://c.saavncdn.com/866/On-My-Way-English-2019-20190308195918-500x500.jpg"
                ),

                )


            CMPAudioPlayer(
                modifier = Modifier,
                url = "https://www.youtube.com/watch?v=NTa6Xbzfq1U.mp3",
                isPause = isPause,
                totalTime = { totalTime = it }, // Update total time of the audio
                currentTime = {
                    if (isSliding.not()) {
                        currentTime = it // Update current playback time
                        sliderTime = null // Reset slider time if not sliding
                    }
                },
                isSliding = isSliding, // Pass seek bar sliding state
                sliderTime = sliderTime, // Pass seek bar slider time,
                isRepeat = isRepeat,
                loadingState = { isLoading = it },
                didEndAudio = {
                }
            )

            DisposableEffect(key1 = lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        isPause = false
                    } else if (event == Lifecycle.Event.ON_STOP) {
                        isPause = true
                    }
                }

                // Add the observer to the lifecycle
                lifecycleOwner.lifecycle.addObserver(observer)

                // When the effect leaves the Composition, remove the observer
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                backgroundColor = Colors.SurfaceWhite
            ) {
                NavHost(navController = navController, startDestination = "main_screen") {
                    composable("main_screen") {
                        MainScreen(
                            onNavigateToSinglePlayer = {
                                navController.navigate("selection_screen")
                            },
                            onNavigateToMultiPlayer = {
                                navController.navigate("multi_player")
                            },
                            onSettingClick = {
                                navController.navigate("setting_screen")
                            }
                        )
                    }
                    composable("single_player") {
                        SinglePlayerScreen(
                            onBack = {
                                navController.navigateUp()
                            },
                            onSettingClick = {
                                navController.navigate("setting_screen")
                            }
                        )
                    }
                    composable("multi_player") {
                        MultiplayerScreen(
                            onBack = {
                                navController.navigateUp()
                            },
                            onSettingClick = {
                                navController.navigate("setting_screen")
                            }
                        )
                    }
                    composable("selection_screen") {
                        SelectionScreen(onBack = {
                            navController.navigateUp()
                        },
                            onNavigateToSinglePlayer = {
                                navController.navigate("single_player") {
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                    composable("setting_screen") {
                        SettingScreen(
                            onBack = {
                                navController.navigateUp()
                            },
                            onSave = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}
