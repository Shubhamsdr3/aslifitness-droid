package com.aslifitness.fitracker.filedownloader

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.media3.exoplayer.offline.Download
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class OfflineVideoViewModel(application: Application) : AndroidViewModel(application) {

    private val _downloads: MutableLiveData<List<Download>> = MutableLiveData()
    val downloads: LiveData<List<Download>>
        get() = _downloads

    private var job: CompletableJob = SupervisorJob()
    private var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + job)

    fun startFlow(context: Context) {
        coroutineScope.launch {
            DownloadUtil.getDownloadTracker(context).getAllDownloadProgressFlow().collect {
                _downloads.postValue(it)
            }
        }
    }

    fun stopFlow() {
        coroutineScope.cancel()
    }
}
