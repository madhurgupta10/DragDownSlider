package com.madhurgupta.dragdownslider.sampleApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.madhurgupta.dragdownslider.DragDownSlider
import com.madhurgupta.dragdownslider.sampleApp.ui.theme.SampleAppForDragDownSlider

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleAppForDragDownSlider {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DragDownSlider()
                }
            }
        }
    }
}