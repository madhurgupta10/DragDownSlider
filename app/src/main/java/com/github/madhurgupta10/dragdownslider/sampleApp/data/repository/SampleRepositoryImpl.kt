package com.github.madhurgupta10.dragdownslider.sampleApp.data.repository

import com.github.madhurgupta10.dragdownslider.sampleApp.data.model.Response
import com.github.madhurgupta10.dragdownslider.sampleApp.data.remote.MockletsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SampleRepositoryImpl @Inject constructor(
    private val api: MockletsApi,
    private val dispatcher: CoroutineDispatcher
) : SampleRepository {

    // Gets the response from the mocklets API
    override suspend fun getResponse(isSuccess: Boolean): Response? = withContext(dispatcher) {
        if (isSuccess) {
            api.getResponse("success_case")
        } else {
            api.getResponse("failure_case")
        }
    }

}