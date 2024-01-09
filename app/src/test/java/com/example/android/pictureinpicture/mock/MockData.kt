package com.example.android.pictureinpicture.mock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.pictureinpicture.StopwatchRepository

internal class FakeStopwatchRepository : StopwatchRepository {
    private val _timeMillisData = MutableLiveData(0L)
    override val timeMillis: LiveData<Long> = _timeMillisData
    private val _started = MutableLiveData(false)
    override val started: LiveData<Boolean> = _started

    override fun startOrPause() {
        _started.value = true
        updateMillis(1000)
    }
    override fun clear() {
        _started.value = false
        _timeMillisData.value = 0L
    }

    fun updateMillis(millis: Long) {
        _timeMillisData.value = millis
    }
}