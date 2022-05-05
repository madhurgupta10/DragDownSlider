package com.madhurgupta.dragdownslider

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
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

@ExperimentalMaterialApi
@Composable
fun DragDownSlider() {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val cardSize = screenWidth
    val circleSize = 100.dp
    val distanceBetweenCircles = 300.dp

    val startPosition = 0
    val endPosition = 1

    val isDragEnabled = rememberSaveable { mutableStateOf(true) }
    val swipeableState = rememberSwipeableState(startPosition)
    val distanceBetweenCirclesInPx =
        with(LocalDensity.current) { (distanceBetweenCircles - circleSize).toPx() }
    val anchors =
        mapOf(0f to startPosition, distanceBetweenCirclesInPx to endPosition) // Maps anchor points (in px) to states

    val scope = rememberCoroutineScope()
    if (swipeableState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
//                isDragEnabled.value = false
                when (swipeableState.currentValue) {
                    endPosition -> {
                        scope.launch {
                            swipeableState.snapTo(startPosition)
                        }
                    }
                }
            }
        }
    }
//    if (swipeableState.currentValue == endPosition) {
//        DisposableEffect(Unit) {
//            onDispose {
//                isDragEnabled.value = false
//            }
//        }
//    }

    Column(
        modifier = Modifier
            .height(cardSize + distanceBetweenCircles + circleSize)
            .padding(24.dp),
    ) {
        Box {
            Card(
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color.White,
                modifier = Modifier.size(cardSize)
            ) {}
            Column(
                modifier = Modifier
                    .height(distanceBetweenCircles)
                    .align(Alignment.BottomCenter)
                    .offset(y = distanceBetweenCircles - circleSize / 2)
                    .swipeable(
                        enabled = isDragEnabled.value,
                        state = swipeableState,
                        anchors = anchors,
                        velocityThreshold = Dp.Infinity,
                        thresholds = { _, _ -> FractionalThreshold(1f) },
                        orientation = Orientation.Vertical,
                    )
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
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