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

    // This state represents network call state, after a slide event is completed
    var responseState by mutableStateOf(OnSlideCompleteState.Loading)
        private set

    // If the circle can be dragged or not
    var isDragEnabled by mutableStateOf(true)
        private set

    // Controls the success and failure scenarios for API
    var isSuccessCase by mutableStateOf(false)
        private set

    // List of error messages to be displayed
    var errorMessages = mutableStateListOf<String>()
        private set

    // Toggles the state of success and failure scenarios
    fun toggle() {
        isSuccessCase = !isSuccessCase
        isDragEnabled = true
        responseState = OnSlideCompleteState.Error
    }

    // Gets the response from network and updates the states accordingly
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

    // After an error is shown on screen it removes it from the list
    fun errorShown() {
        errorMessages.remove(errorMessages.first())
    }

    // Updates the state if network call was successful
    private fun onSuccess() {
        isDragEnabled = false
        responseState = OnSlideCompleteState.Success
    }

    // Updates the states if network call was failed, accepts message param to be displayed as a toast
    private fun onError(message: String) {
        isDragEnabled = true
        responseState = OnSlideCompleteState.Error
        sendMessage(message)
    }

    // Accepts message param to be displayed as a toast and add them to the list
    private fun sendMessage(message: String) {
        errorMessages.add(message)
    }

}