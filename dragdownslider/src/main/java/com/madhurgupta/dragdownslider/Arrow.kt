package com.madhurgupta.dragdownslider

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun Arrow(
    color: Color,
    distance: Dp,
    draggableSize: Dp,
    modifier: Modifier
) {
    Icon(
        Icons.Filled.KeyboardArrowDown,
        "arrow",
        tint = color,
        modifier = modifier
            .size(draggableSize / 4)
            .offset(y = (distance - draggableSize) / 2)
    )
}