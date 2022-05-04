package com.madhurgupta.drag_down_animation.sample_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.madhurgupta.drag_down_animation.DragDownAnimation
import com.madhurgupta.drag_down_animation.sample_app.ui.theme.SampleAppForDragDownAnimation

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleAppForDragDownAnimation {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DragDownAnimation()
                }
            }
        }
    }
}