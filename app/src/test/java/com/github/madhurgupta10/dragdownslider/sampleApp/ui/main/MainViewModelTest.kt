package com.github.madhurgupta10.dragdownslider.sampleApp.ui.main

import androidx.compose.runtime.mutableStateListOf
import com.github.madhurgupta10.dragdownslider.OnSlideCompleteState
import com.github.madhurgupta10.dragdownslider.sampleApp.data.FakeSampleRepository
import com.github.madhurgupta10.dragdownslider.sampleApp.data.repository.SampleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Field

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: SampleRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = FakeSampleRepository()
        viewModel = MainViewModel(repository)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test toggle`() {
        Assert.assertEquals(false, viewModel.isSuccessCase)
        Assert.assertEquals(true, viewModel.isDragEnabled)
        Assert.assertEquals(OnSlideCompleteState.Loading, viewModel.responseState)
        viewModel.toggle()
        Assert.assertEquals(true, viewModel.isSuccessCase)
        Assert.assertEquals(true, viewModel.isDragEnabled)
        Assert.assertEquals(OnSlideCompleteState.Error, viewModel.responseState)
        viewModel.toggle()
        Assert.assertEquals(false, viewModel.isSuccessCase)
        Assert.assertEquals(true, viewModel.isDragEnabled)
        Assert.assertEquals(OnSlideCompleteState.Error, viewModel.responseState)
    }

    @Test
    fun `test errorShown`() {
        val errorMessages: Field = MainViewModel::class.java.getDeclaredField("errorMessages")
        errorMessages.isAccessible = true
        errorMessages.set(viewModel, mutableStateListOf("test1", "test2"))
        Assert.assertEquals("test1", viewModel.errorMessages.first())
        viewModel.errorShown()
        Assert.assertEquals("test2", viewModel.errorMessages.first())
        viewModel.errorShown()
        Assert.assertEquals(true, viewModel.errorMessages.isEmpty())
    }

    @Test
    fun `test getResponse case API failure`() = runTest {
        Assert.assertEquals(false, viewModel.isSuccessCase)
        Assert.assertEquals(true, viewModel.isDragEnabled)
        Assert.assertEquals(OnSlideCompleteState.Loading, viewModel.responseState)
        viewModel.getResponse()
        Assert.assertEquals(true, viewModel.isDragEnabled)
        Assert.assertEquals(OnSlideCompleteState.Error, viewModel.responseState)
        Assert.assertEquals("API Error", viewModel.errorMessages.first())
    }

    @Test
    fun `test getResponse case NPE failure`() = runTest {
        repository = FakeSampleRepository(returnNull = true)
        viewModel = MainViewModel(repository)
        Assert.assertEquals(false, viewModel.isSuccessCase)
        Assert.assertEquals(true, viewModel.isDragEnabled)
        Assert.assertEquals(OnSlideCompleteState.Loading, viewModel.responseState)
        viewModel.getResponse()
        Assert.assertEquals(true, viewModel.isDragEnabled)
        Assert.assertEquals(OnSlideCompleteState.Error, viewModel.responseState)
        Assert.assertEquals("NPE Error", viewModel.errorMessages.first())
    }

    @Test
    fun `test getResponse case Exception failure`() = runTest {
        repository = FakeSampleRepository(throwException = true)
        viewModel = MainViewModel(repository)
        Assert.assertEquals(false, viewModel.isSuccessCase)
        Assert.assertEquals(true, viewModel.isDragEnabled)
        Assert.assertEquals(OnSlideCompleteState.Loading, viewModel.responseState)
        viewModel.getResponse()
        advanceTimeBy(600) // should be slightly greater than delay time
        Assert.assertEquals(true, viewModel.isDragEnabled)
        Assert.assertEquals(OnSlideCompleteState.Error, viewModel.responseState)
        Assert.assertEquals(
            "Error - java.lang.Exception: Test Exception",
            viewModel.errorMessages.first()
        )
    }

    @Test
    fun `test getResponse case success`() = runTest {
        Assert.assertEquals(OnSlideCompleteState.Loading, viewModel.responseState)
        Assert.assertEquals(true, viewModel.isDragEnabled)
        viewModel.toggle()
        Assert.assertEquals(true, viewModel.isSuccessCase)
        viewModel.getResponse()
        Assert.assertEquals(false, viewModel.isDragEnabled)
        Assert.assertEquals(OnSlideCompleteState.Success, viewModel.responseState)
        Assert.assertEquals(true, viewModel.errorMessages.isEmpty())
    }

}