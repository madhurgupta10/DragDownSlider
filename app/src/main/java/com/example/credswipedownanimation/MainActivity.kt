package com.example.credswipedownanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.credswipedownanimation.ui.theme.CredSwipedownAnimationTheme
import kotlin.math.roundToInt

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CredSwipedownAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
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
            }
        }
    }
}