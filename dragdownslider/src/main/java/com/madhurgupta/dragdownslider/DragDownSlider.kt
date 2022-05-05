package com.madhurgupta.dragdownslider

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalMaterialApi
@Composable
fun DragDownSlider() {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val cardSize = screenWidth
    val circleSize = 100.dp
    val distanceBetweenCircles = 300.dp

    val startPosition = 0
    val endPosition = 1

    val distanceBetweenCirclesInPx =
        with(density) { (distanceBetweenCircles - circleSize).toPx() }
    val anchors =
        mapOf(
            0f to startPosition,
            distanceBetweenCirclesInPx to endPosition
        ) // Maps anchor points (in px) to states

    val isDragEnabled by rememberSaveable { mutableStateOf(true) }
    var showCompactCard by rememberSaveable { mutableStateOf(true) }
    val swipeableState = rememberSwipeableState(startPosition)
    val scope = rememberCoroutineScope()


    if (swipeableState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
//                isDragEnabled.value = false
                when (swipeableState.currentValue) {
                    endPosition -> {
                        showCompactCard = false
                        scope.launch {
//                            swipeableState.snapTo(startPosition) //onfail
                        }
                    }
                }
            }
        }
    }
//    if (swipeableState.currentValue == endPosition) {
//        DisposableEffect(Unit) {
//            onDispose {
//                isDragEnabled.value = false // disable slider
//            }
//        }
//    }

    Column(
        modifier = Modifier
            .height(
                if (showCompactCard) {
                    cardSize + distanceBetweenCircles + circleSize
                } else {
                    cardSize + circleSize
                }
            )
            .padding(24.dp),
    ) {
        Box {
            Card(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color.White,
                modifier = Modifier
                    .width(cardSize)
                    .height(
                        if (showCompactCard) {
                            cardSize
                        } else {
                            cardSize + circleSize
                        }
                    )
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearOutSlowInEasing
                        )
                    )
            ) {}
            if (showCompactCard) {
                Icon(
                    Icons.Filled.KeyboardArrowDown,
                    "arrow",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(circleSize / 4)
                        .align(Alignment.BottomCenter)
                        .offset(y = (distanceBetweenCircles - circleSize) / 2)
                )
                Column(
                    modifier = Modifier
                        .height(distanceBetweenCircles)
                        .align(Alignment.BottomCenter)
                        .offset(y = distanceBetweenCircles - circleSize / 2)
                        .swipeable(
                            enabled = isDragEnabled,
                            state = swipeableState,
                            anchors = anchors,
                            velocityThreshold = Dp.Infinity,
                            thresholds = { _, _ -> FractionalThreshold(1f) },
                            orientation = Orientation.Vertical,
                        )
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    val state = remember {
                        MutableTransitionState(false).apply {
                            // Start the animation immediately.
                            targetState = true
                        }
                    }
                    Card(
                        shape = CircleShape,
                        elevation = 4.dp,
                        backgroundColor = Color.Black,
                        modifier = Modifier
                            .width(circleSize)
                            .aspectRatio(1f)
                            .offset {
                                IntOffset(0, swipeableState.offset.value.roundToInt())
                            }
                            .clip(CircleShape)
                            .zIndex(1f)
                    ) {}

                    Card(
                        shape = CircleShape,
                        elevation = 0.dp,
                        contentColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        border = BorderStroke(1.dp, Color.LightGray),
                        modifier = Modifier
                            .size(circleSize)
                            .clip(CircleShape)
                    ) {}
                }
            }
        }
    }
}