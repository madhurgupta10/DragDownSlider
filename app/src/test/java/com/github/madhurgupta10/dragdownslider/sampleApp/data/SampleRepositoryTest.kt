package com.github.madhurgupta10.dragdownslider.sampleApp.data

import com.github.madhurgupta10.dragdownslider.sampleApp.data.repository.SampleRepository
import com.github.madhurgupta10.dragdownslider.sampleApp.data.repository.SampleRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SampleRepositoryTest {

    private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: SampleRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = SampleRepositoryImpl(
            api = FakeMockletsApi(),
            dispatcher = dispatcher
        )
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `success case`() = runTest {
        val response = repository.getResponse(true)
        Assert.assertEquals(true, response?.success)
    }

    @Test
    fun `failure case`() = runTest {
        val response = repository.getResponse(false)
        Assert.assertEquals(false, response?.success)
    }

}