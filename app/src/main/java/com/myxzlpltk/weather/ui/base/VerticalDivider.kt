package com.myxzlpltk.weather.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val DividerAlpha = 0.12f

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = DividerAlpha),
    thickness: Dp = 1.dp
) {
    Box(
        modifier
            .fillMaxHeight()
            .width(thickness)
            .background(color = color)
    )
}

@Preview
@Composable
private fun PreviewVerticalDivider() {
    VerticalDivider()
}