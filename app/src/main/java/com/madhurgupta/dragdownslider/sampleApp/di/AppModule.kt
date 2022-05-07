package com.madhurgupta.dragdownslider.sampleApp.di

import com.madhurgupta.dragdownslider.sampleApp.BuildConfig
import com.madhurgupta.dragdownslider.sampleApp.data.remote.MockletsApi
import com.madhurgupta.dragdownslider.sampleApp.data.repository.SampleRepository
import com.madhurgupta.dragdownslider.sampleApp.data.repository.SampleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMockletsApi(): MockletsApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MockletsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideSampleRepository(
        api: MockletsApi,
        dispatcher: CoroutineDispatcher
    ): SampleRepository {
        return SampleRepositoryImpl(api, dispatcher)
    }

}