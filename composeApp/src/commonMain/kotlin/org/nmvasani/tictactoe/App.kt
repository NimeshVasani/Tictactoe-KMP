package org.nmvasani.tictactoe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.nmvasani.tictactoe.di.viewModelModule
import org.nmvasani.tictactoe.ui.colors.Colors
import org.nmvasani.tictactoe.ui.screens.MainScreen
import org.nmvasani.tictactoe.ui.screens.PlayingScreen
import org.nmvasani.tictactoe.ui.screens.SelectionScreen

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(viewModelModule)
    }) {
        MaterialTheme {
            val navController = rememberNavController()
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
                            }
                        )
                    }
                    composable("single_player") {
                        PlayingScreen()
                    }
                    composable("multi_player") {
                        PlayingScreen()
                    }
                    composable("selection_screen") {
                        SelectionScreen(onBack = {
                            navController.navigateUp()
                        },
                            onNavigateToSinglePlayer = {
                                navController.navigate("single_player")
                            }
                        )
                    }
                }
            }
        }
    }
}
