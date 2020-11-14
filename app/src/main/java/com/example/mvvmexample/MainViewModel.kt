package com.example.mvvmexample

import androidx.lifecycle.MutableLiveData
import com.yanghao277dev.mvvmbase.base.LoadState
import kotlinx.coroutines.async

class MainViewModel : BaseViewModel() {
    val imageLiveUrl = MutableLiveData<String>()

    private val searchResult = mutableListOf<Hits>()
    var current = -1

    fun getBingImage(){
        launch({
            loadState.postValue(LoadState.Loading("request"))
            val bingBeanRef = async { mModel?.getBingBean("js",1) }
            bingBeanRef.await()?.let{
                val imageUrl = ApiService.BASE_URL + it.images!![0]!!.url
                imageLiveUrl.postValue(imageUrl)
                loadState.postValue(LoadState.Success("request"))
            }

        },{
            loadState.postValue(LoadState.Fail(it.message ?: "error"))
        })
    }

    fun next(){
        current++
        if(searchResult.isEmpty()){
            getImageByKey(current,"sexy")
        }else{
            imageLiveUrl.postValue(searchResult[current % searchResult.size].largeImageURL)
        }

    }

    fun pre(){
        if(current-1 < 0){
            return
        }
        current--
        if(searchResult.isEmpty()){
            getImageByKey(current,"sexy")
        }else{
            imageLiveUrl.postValue(searchResult[current % searchResult.size].largeImageURL)
        }
    }


    private fun getImageByKey(position : Int,key : String){
        launch({
            loadState.postValue(LoadState.Loading("request2"))
            val pixabayRef = async { mModel?.getPixabayBean(key) }
            pixabayRef.await()?.let{
                searchResult.addAll(it.hits!!.toTypedArray())
                val imageUrl = it.hits!![position % it.hits!!.size].largeImageURL
                imageLiveUrl.postValue(imageUrl)
                loadState.postValue(LoadState.Success("request2"))
            }

        },{
            loadState.postValue(LoadState.Fail(it.message ?: "error2"))
        })
    }
}