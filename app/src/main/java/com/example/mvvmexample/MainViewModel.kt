package com.example.mvvmexample

import androidx.lifecycle.MutableLiveData
import com.yanghao277dev.mvvmbase.base.LoadState
import kotlinx.coroutines.async

class MainViewModel : BaseViewModel() {
    val imageLiveUrl = MutableLiveData<String>()

    fun getBingImage(){
        launch({
            val bingBeanRef = async { mModel?.getBingBean("js",1) }
            bingBeanRef.await()?.let{
                val imageUrl = ApiService.BASE_URL + it.images!![0]!!.url
                imageLiveUrl.postValue(imageUrl)
            }

        },{
            loadState.postValue(LoadState.Fail(it.message ?: "error"))
        })
    }
}