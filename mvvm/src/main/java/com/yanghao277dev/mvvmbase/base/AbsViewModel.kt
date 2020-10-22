package com.yanghao277dev.mvvmbase.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

abstract class AbsViewModel<M> : ViewModel() {
    var mModel: M? = null

    val loadState = MutableLiveData<LoadState>()

    override fun onCleared() {
        super.onCleared()
        mModel = null
    }

    fun launch(block: suspend CoroutineScope.() -> Unit,
        onError: (e: Throwable) -> Unit = {}, onComplete: () -> Unit = {}) {
        viewModelScope.launch(CoroutineExceptionHandler { _, e -> onError(e) }) {
            withContext(Dispatchers.IO){
                try { block.invoke(this) }
                finally { onComplete() }
            }
        }
    }
}