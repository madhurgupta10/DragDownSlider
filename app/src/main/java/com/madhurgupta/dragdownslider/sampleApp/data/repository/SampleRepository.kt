package com.madhurgupta.dragdownslider.sampleApp.data.repository

import com.madhurgupta.dragdownslider.sampleApp.data.model.Response

interface SampleRepository {

    suspend fun getResponse(isSuccess: Boolean): Response?

}