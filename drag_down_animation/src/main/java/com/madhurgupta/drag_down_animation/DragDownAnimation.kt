package com.madhurgupta.drag_down_animation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun DragDownAnimation() {
    val height = 300.dp
    val squareSize = 100.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { height.toPx() }
    val anchors =
        mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

    Box(
        modifier = Modifier
            .padding(100.dp)
            .swipeable(
                enabled = swipeableState.offset.value.roundToInt() != 1,
                state = swipeableState,
                anchors = anchors,
                velocityThreshold = Dp.Infinity,
                thresholds = { _, _ -> FractionalThreshold(1f) },
                orientation = Orientation.Vertical
            )
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
                .size(squareSize)
                .background(Color.DarkGray)
        )
    }
}