package com.github.madhurgupta10.dragdownslider

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

@Composable
fun DragTarget(
    shape: Shape,
    color: Color,
    elevation: Dp,
    border: BorderStroke,
    size: Dp,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Card(
        shape = shape,
        elevation = elevation,
        backgroundColor = color,
        border = border,
        modifier = modifier
            .size(size)
            .clip(shape)
    ) {
        content()
    }
}