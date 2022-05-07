package com.madhurgupta.dragdownslider.sampleApp.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.madhurgupta.dragdownslider.OnSlideCompleteState
import com.madhurgupta.dragdownslider.sampleApp.data.repository.SampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
            val response = repository.getResponse(isSuccessCase)
            if (response != null) {
                if (response.success) {
                    onSuccess()
                } else {
                    onError()
                }
            } else {
                onError()
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

    private fun onError() {
        isDragEnabled = true
        responseState = OnSlideCompleteState.Error
        sendMessage("Error")
    }

    private fun sendMessage(message: String) {
        errorMessages.add(message)
    }

}