package com.github.madhurgupta10.dragdownslider.sampleApp.data

import com.github.madhurgupta10.dragdownslider.sampleApp.data.model.Response
import com.github.madhurgupta10.dragdownslider.sampleApp.data.repository.SampleRepository

class FakeSampleRepository(
    val throwException: Boolean = false,
    val returnNull: Boolean = false,
): SampleRepository {

    override suspend fun getResponse(isSuccess: Boolean): Response? {
        if (returnNull) {
            return null
        }
        if (throwException) {
            throw Exception("Test Exception")
        }
        return if (isSuccess) {
            Response(success = true)
        } else {
            Response(success = false)
        }
    }

}