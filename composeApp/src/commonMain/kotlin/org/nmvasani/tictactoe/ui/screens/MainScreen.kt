package org.nmvasani.tictactoe.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.nmvasani.tictactoe.ui.colors.Colors
import org.nmvasani.tictactoe.ui.composables.RotatingIcon
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.mainlogo

@Composable
fun MainScreen(
    onNavigateToSinglePlayer: () -> Unit,
    onNavigateToMultiPlayer: () -> Unit
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



