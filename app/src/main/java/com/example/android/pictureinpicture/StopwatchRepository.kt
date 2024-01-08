package com.example.android.pictureinpicture

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface StopwatchRepository {
    val timeMillis: LiveData<Long>
    val started: LiveData<Boolean>
    fun startOrPause()
    fun clear()
}
class DefaultStopwatchRepository @Inject constructor() : StopwatchRepository {

    private val _timeMillis = MutableLiveData(0L)
    override val timeMillis: LiveData<Long> = _timeMillis
    private var job: Job? = null

    private var startUptimeMillis = SystemClock.uptimeMillis()
    private val _started = MutableLiveData(false)

    override val started: LiveData<Boolean> = _started

    /**
     * Starts the stopwatch if it is not yet started, or pauses it if it is already started.
     */
    @OptIn(DelicateCoroutinesApi::class)
    override fun startOrPause() {
        if (_started.value == true) {
            _started.value = false
            job?.cancel()
        } else {
            _started.value = true
            job = GlobalScope.launch { start() }
        }
    }

    private suspend fun CoroutineScope.start() {
        startUptimeMillis = SystemClock.uptimeMillis() - (_timeMillis.value ?: 0L)
        while (isActive) {
            withContext(Dispatchers.Main) {
                _timeMillis.value = SystemClock.uptimeMillis() - startUptimeMillis
                // Updates on every render frame.
                awaitFrame()
            }
        }
    }

    /**
     * Clears the stopwatch to 00:00:00.
     */
    override fun clear() {
        startUptimeMillis = SystemClock.uptimeMillis()
        _timeMillis.value = 0L
    }
}