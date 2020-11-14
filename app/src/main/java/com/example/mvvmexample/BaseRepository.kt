package com.example.mvvmexample

import com.yanghao277dev.mvvmbase.http.HttpHelper

class BaseRepository {
    private var apiService: ApiService? = null

    init {
        if (null == apiService) {
            apiService = HttpHelper.instance.create(ApiService::class.java)
        }
    }

    companion object{
        val INSTANCE = BaseRepository()
    }

    suspend fun getBingBean(format : String,n : Int) : BingBean? {
        return  apiService?.getBingImage(format,n)
    }

    suspend fun getPixabayBean(key : String) : PixabayBean? {
        HttpHelper.instance.resetBaseUrlWithKey("key",ApiService.BASE_URL2)
        return  apiService?.getImageByKey("19108431-4431761d4db585aaf41bf35ef",key,"photo")
    }
}