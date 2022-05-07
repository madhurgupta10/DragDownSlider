package com.madhurgupta.dragdownslider

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun Draggable(
    shape: Shape,
    color: Color,
    elevation: Dp,
    size: Dp,
    modifier: Modifier,
    sliderState: SwipeableState<SliderState>,
    content: @Composable () -> Unit
) {
    Card(
        shape = shape,
        elevation = elevation,
        backgroundColor = color,
        modifier = modifier
            .width(size)
            .aspectRatio(1f)
            .offset {
                IntOffset(0, sliderState.offset.value.roundToInt())
            }
            .clip(shape)
            .zIndex(1f)
    ) {
        content()
    }
}