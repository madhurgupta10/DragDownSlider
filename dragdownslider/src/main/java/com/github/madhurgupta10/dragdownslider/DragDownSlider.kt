package com.github.madhurgupta10.dragdownslider

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun DragDownSlider(
    modifier: Modifier = Modifier,
    arrowColor: Color = Color.Green,
    dragTargetShape: Shape = CircleShape,
    dragTargetColor: Color = Color.Transparent,
    dragTargetBorder: BorderStroke = BorderStroke(1.dp, Color.LightGray),
    dragTargetModifier: Modifier = Modifier,
    dragTargetContent: @Composable () -> Unit = {},
    draggableSize: Dp = 100.dp,
    draggableShape: Shape = CircleShape,
    draggableColor: Color = Color.Black,
    draggableContent: @Composable () -> Unit = {},
    draggableModifier: Modifier = Modifier,
    distance: Dp = 300.dp,
    compactCardSize: Dp,
    expandCardSize: Dp = compactCardSize + draggableSize,
    parentCardModifier: Modifier = Modifier,
    parentCardContent: @Composable () -> Unit = {},
    sliderState: SwipeableState<SliderState>,
    onSlideCompleteState: OnSlideCompleteState,
    isDragEnabled: Boolean,
    onSlideComplete: () -> Unit,
    onSuccess: () -> Unit = {},
    onError: () -> Unit = {},
    onLoading: () -> Unit = {},
) {
    if (compactCardSize >= expandCardSize) {
        throw IllegalArgumentException("expandCardSize should be greater than compactCardSize")
    }

    if (draggableSize >= compactCardSize) {
        throw IllegalArgumentException("draggableSize should be less than compactCardSize")
    }

    if (distance <= (draggableSize + draggableSize)) {
        throw IllegalArgumentException("distance should be greater than twice the size of draggable")
    }

    val distanceInPx =
        with(LocalDensity.current) { (distance - draggableSize).toPx() }
    val anchors =
        mapOf(
            0f to SliderState.Start,
            distanceInPx to SliderState.End
        ) // Maps anchor points (in px) to states

    if (sliderState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                if (sliderState.currentValue == SliderState.End) {
                    onSlideComplete.invoke()
                }
            }
        }
    }

    LaunchedEffect(onSlideCompleteState) {
        when (onSlideCompleteState) {
            OnSlideCompleteState.Success -> {
                onSuccess.invoke()
                sliderState.snapTo(SliderState.End)
            }
            OnSlideCompleteState.Error -> {
                onError.invoke()
                sliderState.snapTo(SliderState.Start)
            }
            OnSlideCompleteState.Loading -> {
                onLoading.invoke()
            }
        }
    }

    Column(
        modifier = modifier
            .width(compactCardSize)
            .height(
                if (onSlideCompleteState == OnSlideCompleteState.Success) {
                    expandCardSize
                } else {
                    compactCardSize + distance
                }
            )
            .padding(24.dp),
    ) {
        Box {
            ParentCard(
                compactCardSize = compactCardSize,
                expandCardSize = expandCardSize,
                showExpandedCard = onSlideCompleteState == OnSlideCompleteState.Success,
                modifier = parentCardModifier
            ) {
                parentCardContent()
            }
            if (onSlideCompleteState != OnSlideCompleteState.Success) {
                Arrow(
                    color = arrowColor,
                    distance = distance,
                    draggableSize = draggableSize,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
                Column(
                    modifier = Modifier
                        .height(distance)
                        .align(Alignment.BottomCenter)
                        .offset(y = distance - draggableSize / 2)
                        .swipeable(
                            enabled = isDragEnabled,
                            state = sliderState,
                            anchors = anchors,
                            velocityThreshold = Dp.Infinity,
                            thresholds = { _, _ -> FractionalThreshold(1f) },
                            orientation = Orientation.Vertical,
                        )
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Draggable(
                        shape = draggableShape,
                        color = draggableColor,
                        size = draggableSize,
                        elevation = 4.dp,
                        modifier = draggableModifier,
                        sliderState = sliderState,
                    ) {
                        draggableContent()
                    }
                    DragTarget(
                        shape = dragTargetShape,
                        color = dragTargetColor,
                        size = draggableSize,
                        elevation = 0.dp,
                        border = dragTargetBorder,
                        modifier = dragTargetModifier
                    ) {
                        dragTargetContent()
                    }
                }
            }
        }
    }
}