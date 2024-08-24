package org.nmvasani.tictactoe

import AudioPlayer
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.nmvasani.tictactoe.di.viewModelModule
import org.nmvasani.tictactoe.ui.colors.Colors
import org.nmvasani.tictactoe.ui.screens.MainScreen
import org.nmvasani.tictactoe.ui.screens.MultiplayerScreen
import org.nmvasani.tictactoe.ui.screens.SelectionScreen
import org.nmvasani.tictactoe.ui.screens.SettingScreen
import org.nmvasani.tictactoe.ui.screens.SinglePlayerScreen
import org.nmvasani.tictactoe.viewmodels.SettingViewModel

@Composable
@Preview
fun App(
    prefs: DataStore<Preferences>,
    audioPlayer: AudioPlayer
) {
    KoinApplication(application = {
        modules(viewModelModule)
    }) {
        MaterialTheme {

            val settingViewModel: SettingViewModel = koinInject()
            val lifecycleOwner = LocalLifecycleOwner.current

            LaunchedEffect(key1 = lifecycleOwner) {
                settingViewModel.loadSettings(prefs)
            }

            // settingViewModel.loadSettings(prefs)
            // State for pausing/resuming audio
            val isPause = settingViewModel.isPause.collectAsState()

            val player1Name = settingViewModel.player1Name.collectAsState()
            val player2Name = settingViewModel.player2Name.collectAsState()

            val navController = rememberNavController()


            LaunchedEffect(isPause.value) {
                if (isPause.value)
                    audioPlayer.release()
                else
                    audioPlayer.playSound(0)

            }

            DisposableEffect(key1 = lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    if (event == Lifecycle.Event.ON_RESUME) {
                        settingViewModel.loadSettings(prefs)
                    } else if (event == Lifecycle.Event.ON_STOP || event == Lifecycle.Event.ON_PAUSE) {
                        settingViewModel.setPause(true)
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
                modifier = Modifier.fillMaxSize(), backgroundColor = Colors.SurfaceWhite
            ) {
                NavHost(navController = navController, startDestination = "main_screen") {
                    composable("main_screen",

                        popEnterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(700)
                            )
                        }, popExitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(700)
                            )
                        }, exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(700)
                            )
                        }) {

                        MainScreen(onNavigateToSinglePlayer = {
                            navController.navigate("selection_screen")
                        }, onNavigateToMultiPlayer = {
                            navController.navigate("multi_player")
                        }, onSettingClick = {
                            navController.navigate("setting_screen")
                        })
                    }
                    composable(
                        "selection_screen",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(700)
                            )
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(700)
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(700)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(700)
                            )
                        },
                    ) {
                        SelectionScreen(onBack = {
                            navController.navigateUp()
                        }, onNavigateToSinglePlayer = {
                            navController.navigate("single_player") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                    }
                    composable("single_player", enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    },

                        popEnterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(700)
                            )
                        }, popExitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(700)
                            )
                        }) {
                        SinglePlayerScreen(
                            player1Name = player1Name.value,
                            onBack = {
                                navController.navigateUp()
                            }, onSettingClick = {
                                navController.navigate("setting_screen")
                            }
                        )
                    }
                    composable(
                        "multi_player",
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(700)
                            )
                        },
                        popEnterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(700)
                            )
                        },
                        popExitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(700)
                            )
                        },
                    ) {
                        MultiplayerScreen(
                            player1Name = player1Name.value,
                            player2Name = player2Name.value,
                            onBack = {
                                navController.navigateUp()
                            }, onSettingClick = {
                                navController.navigate("setting_screen")
                            }
                        )
                    }

                    composable("setting_screen", enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    }, popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }, popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }) {
                        SettingScreen(
                            onBack = {
                                navController.navigateUp()
                            },
                            onSave = {
                                settingViewModel.saveSettings(prefs)
                                navController.navigateUp()
                            },
                        )
                    }
                }
            }
        }
    }
}

