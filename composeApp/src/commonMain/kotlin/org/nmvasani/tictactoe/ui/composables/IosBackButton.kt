package org.nmvasani.tictactoe.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.nmvasani.tictactoe.ui.colors.Colors
import tictactoe.composeapp.generated.resources.Res
import tictactoe.composeapp.generated.resources.baseline_arrow_back_ios_new_24

@Composable
fun IosBackButton(onBack: () -> Unit) {
    Row(
        modifier = Modifier.padding(top = 70.dp, start = 20.dp).height(30.dp)
            .clickable(
                onClick = onBack,
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(
                    bounded = false,
                    radius = 50.dp,
                    color = Colors.SkyBlue
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.baseline_arrow_back_ios_new_24),
            contentDescription = "background",
            tint = Colors.SkyBlue,
            modifier = Modifier.fillMaxHeight(),

            )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Back",
            color = Colors.SkyBlue,
            fontSize = 18.sp,
            fontWeight = W500
        )
    }
}
