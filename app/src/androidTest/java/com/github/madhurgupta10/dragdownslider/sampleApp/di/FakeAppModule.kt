package com.github.madhurgupta10.dragdownslider.sampleApp.di

import com.github.madhurgupta10.dragdownslider.sampleApp.data.remote.MockletsApi
import com.github.madhurgupta10.dragdownslider.sampleApp.data.repository.SampleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import com.github.madhurgupta10.dragdownslider.sampleApp.data.FakeMockletsApi
import com.github.madhurgupta10.dragdownslider.sampleApp.data.FakeSampleRepository
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object FakeAppModule {

    @Provides
    fun provideMockletsApi(): MockletsApi {
        return FakeMockletsApi()
    }

    @Provides
    @Singleton
    fun provideSampleRepository(): SampleRepository {
        return FakeSampleRepository()
    }

}