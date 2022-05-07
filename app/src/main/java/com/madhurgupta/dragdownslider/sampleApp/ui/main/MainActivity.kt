package com.madhurgupta.dragdownslider.sampleApp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madhurgupta.dragdownslider.DragDownSlider
import com.madhurgupta.dragdownslider.SliderState
import com.madhurgupta.dragdownslider.sampleApp.ui.theme.SampleAppForDragDownSlider
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()

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

                    val sliderState = rememberSwipeableState(SliderState.Start)
                    val context = LocalContext.current
                    val isSuccessCase = viewModel.isSuccessCase

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (isSuccessCase) {
                                    "Success Case"
                                } else {
                                    "Error Case"
                                },
                                color = Color.White,
                                fontSize = 24.sp,
                            )
                            Switch(
                                checked = isSuccessCase,
                                onCheckedChange = {
                                    viewModel.toggle()
                                }
                            )
                        }
                        DragDownSlider(
                            compactCardSize = screenWidth,
                            sliderState = sliderState,
                            onSlideCompleteState = viewModel.responseState,
                            isDragEnabled = viewModel.isDragEnabled,
                            onSlideComplete = {
                                viewModel.getResponse()
                            }
                        )
                    }

                    if (viewModel.errorMessages.isNotEmpty()) {
                        val errorMessage = viewModel.errorMessages.first()
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        viewModel.errorShown()
                    }
                }
            }
        }
    }
}