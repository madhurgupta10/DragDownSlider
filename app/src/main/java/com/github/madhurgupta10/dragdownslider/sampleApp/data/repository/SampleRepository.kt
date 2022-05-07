package com.github.madhurgupta10.dragdownslider.sampleApp.data.repository

import com.github.madhurgupta10.dragdownslider.sampleApp.data.model.Response

interface SampleRepository {

    suspend fun getResponse(isSuccess: Boolean): Response?

}