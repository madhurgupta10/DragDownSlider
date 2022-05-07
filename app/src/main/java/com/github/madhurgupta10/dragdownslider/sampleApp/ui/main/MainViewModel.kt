package com.github.madhurgupta10.dragdownslider.sampleApp.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.madhurgupta10.dragdownslider.OnSlideCompleteState
import com.github.madhurgupta10.dragdownslider.sampleApp.data.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SampleRepository
) : ViewModel() {

    var responseState by mutableStateOf(OnSlideCompleteState.Loading)
        private set

    var isDragEnabled by mutableStateOf(true)
        private set

    var isSuccessCase by mutableStateOf(false)
        private set

    var errorMessages = mutableStateListOf<String>()
        private set

    fun toggle() {
        isSuccessCase = !isSuccessCase
        isDragEnabled = true
        responseState = OnSlideCompleteState.Error
    }

    fun getResponse() {
        responseState = OnSlideCompleteState.Loading
        viewModelScope.launch {
            try {
                val response = repository.getResponse(isSuccessCase)
                if (response != null) {
                    if (response.success) {
                        onSuccess()
                    } else {
                        onError("API Error")
                    }
                } else {
                    onError("NPE Error")
                }
            } catch (e: Exception) {
                delay(500) // Required to trigger LaunchEffect on state change
                onError("Error - $e")
            }
        }
    }

    fun errorShown() {
        errorMessages.remove(errorMessages.first())
    }

    private fun onSuccess() {
        isDragEnabled = true
        responseState = OnSlideCompleteState.Success
    }

    private fun onError(message: String) {
        isDragEnabled = true
        responseState = OnSlideCompleteState.Error
        sendMessage(message)
    }

    private fun sendMessage(message: String) {
        errorMessages.add(message)
    }

}