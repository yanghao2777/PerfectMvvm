package com.example.mvvmexample

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object{
        const val BASE_URL = "https://cn.bing.com/"
    }

    @GET("HPImageArchive.aspx")
    suspend fun getBingImage(@Query("format")format : String, @Query("n")n : Int): BingBean

}
