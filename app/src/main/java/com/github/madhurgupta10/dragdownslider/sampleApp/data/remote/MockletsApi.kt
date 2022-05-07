package com.github.madhurgupta10.dragdownslider.sampleApp.data.remote

import com.github.madhurgupta10.dragdownslider.sampleApp.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MockletsApi {

    @GET("p68470/{case}")
    suspend fun getResponse(@Path("case") case: String): Response?

}