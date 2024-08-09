package org.nmvasani.tictactoe.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import org.nmvasani.tictactoe.ui.colors.Colors

@Composable
fun CustomRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color = Colors.SkyBlue,
    unselectedColor: Color = Colors.SkyBlue.copy(alpha = 0.5f),
    size: Int = 24 // Size in dp
) {
    Canvas(
        modifier = Modifier
            .size(size.dp)
            .clickable(onClick = onClick)
    ) {
        val radius = size.dp.toPx() / 2

        // Outer circle
        drawCircle(
            color = if (selected) selectedColor else unselectedColor,
            radius = radius,
            style = Stroke(width = 2.dp.toPx())
        )

        // Inner circle if selected
        if (selected) {
            drawCircle(
                color = selectedColor,
                radius = radius * 0.6f
            )
        }
    }
}