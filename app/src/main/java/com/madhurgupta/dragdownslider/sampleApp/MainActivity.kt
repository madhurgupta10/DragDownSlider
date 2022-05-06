package com.madhurgupta.dragdownslider.sampleApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.madhurgupta.dragdownslider.DragDownSlider
import com.madhurgupta.dragdownslider.SliderState
import com.madhurgupta.dragdownslider.sampleApp.ui.theme.SampleAppForDragDownSlider
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleAppForDragDownSlider {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    color = Color.DarkGray
                ) {
                    val configuration = LocalConfiguration.current
                    val screenWidth = configuration.screenWidthDp.dp

                    val swipeableState = rememberSwipeableState(SliderState.Start)
                    val scope = rememberCoroutineScope()
                    var isDragEnabled by rememberSaveable { mutableStateOf(true) }
                    val isSuccessfulWithoutError by rememberSaveable { mutableStateOf(false) }

                    DragDownSlider(
                        compactCardSize = screenWidth,
                        swipeableState = swipeableState,
                        isSuccessfulWithoutError = isSuccessfulWithoutError,
                        isDragEnabled = isDragEnabled,
                        onSuccess = {
                            isDragEnabled = false
                            scope.launch {
                                swipeableState.snapTo(SliderState.End)
                            }
                        },
                        onFailure = {
                            isDragEnabled = true
                            scope.launch {
                                swipeableState.snapTo(SliderState.Start)
                            }
                        },
                    )
                }
            }
        }
    }
}