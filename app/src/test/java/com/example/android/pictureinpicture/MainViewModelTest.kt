package com.example.android.pictureinpicture

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.pictureinpicture.mock.FakeStopwatchRepository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun timerIsInitialisedWithDefaultValue() {
        val fakeRepository = FakeStopwatchRepository()
        val viewModel = MainViewModel(fakeRepository)

        val timeObserver = Observer<String> {}
        val startedObserver = Observer<Boolean> {}
        try {
            viewModel.time.observeForever(timeObserver)
            viewModel.started.observeForever(startedObserver)

            val timeValue = viewModel.time.value
            assertEquals("00:00:00", timeValue)
            val startedValue = viewModel.started.value
            assertEquals(false, startedValue)
        } finally {
            viewModel.time.removeObserver(timeObserver)
            viewModel.started.removeObserver(startedObserver)
        }
    }

    @Test
    fun given_stopwatchIsStarted_then_timerIsUpdated() {
        val fakeRepository = FakeStopwatchRepository()
        val viewModel = MainViewModel(fakeRepository)

        val timeObserver = Observer<String> {}
        val startedObserver = Observer<Boolean> {}
        try {
            viewModel.time.observeForever(timeObserver)
            viewModel.started.observeForever(startedObserver)

            viewModel.startOrPause()

            val startedValue = viewModel.started.value
            assertEquals(true, startedValue)

            var value = viewModel.time.value
            assertEquals("00:01:00", value)

            fakeRepository.updateMillis(60000)
            value = viewModel.time.value
            assertEquals("01:00:00", value)

            fakeRepository.updateMillis(120000)
            value = viewModel.time.value
            assertEquals("02:00:00", value)

            fakeRepository.updateMillis(852150)
            value = viewModel.time.value
            assertEquals("14:12:15", value)

        } finally {
            viewModel.time.removeObserver(timeObserver)
            viewModel.started.removeObserver(startedObserver)
        }
    }

    @Test
    fun given_stopwatchIsStarted_when_onClear_then_timerIsReset() {
        val fakeRepository = FakeStopwatchRepository()
        val viewModel = MainViewModel(fakeRepository)

        val timeObserver = Observer<String> {}
        val startedObserver = Observer<Boolean> {}
        try {
            viewModel.time.observeForever(timeObserver)
            viewModel.started.observeForever(startedObserver)

            viewModel.startOrPause()

            var startedValue = viewModel.started.value
            assertEquals(true, startedValue)

            var value = viewModel.time.value
            assertEquals("00:01:00", value)

            viewModel.clear()

            startedValue = viewModel.started.value
            assertEquals(false, startedValue)

            value = viewModel.time.value
            assertEquals("00:00:00", value)

        } finally {
            viewModel.time.removeObserver(timeObserver)
            viewModel.started.removeObserver(startedObserver)
        }
    }
}