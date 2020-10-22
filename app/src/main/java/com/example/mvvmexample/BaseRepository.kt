package com.example.mvvmexample

import com.yanghao277dev.mvvmbase.http.HttpHelper

class BaseRepository {
    private var apiService: ApiService? = null

    init {
        if (null == apiService) {
            apiService = HttpHelper.getInstance().create(ApiService::class.java)
        }
    }

    companion object{
        val INSTANCE = BaseRepository()
    }

    suspend fun getBingBean(format : String,n : Int) : BingBean? {
        return  apiService?.getBingImage(format,n)
    }
}