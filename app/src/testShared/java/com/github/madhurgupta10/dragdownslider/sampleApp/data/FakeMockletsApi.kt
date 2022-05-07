package com.github.madhurgupta10.dragdownslider.sampleApp.data

import com.github.madhurgupta10.dragdownslider.sampleApp.data.model.Response
import com.github.madhurgupta10.dragdownslider.sampleApp.data.remote.MockletsApi

class FakeMockletsApi(): MockletsApi {

    override suspend fun getResponse(case: String): Response? {
        if (case == "success_case") {
            return Response(success = true)
        } else if (case == "failure_case") {
            return Response(success = false)
        }
        return null
    }
}