package com.example.mvvmexample

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    companion object{
        const val BASE_URL = "https://cn.bing.com/"

        const val BASE_URL2 = "https://pixabay.com/"
    }

    @GET("HPImageArchive.aspx")
    suspend fun getBingImage(@Query("format")format : String, @Query("n")n : Int): BingBean


    @Headers("Domain-Name: key")
    @GET("api/")
    suspend fun getImageByKey(@Query("key")format : String,
                              @Query("q")searchKey : String,
                              @Query("image_type")type : String): PixabayBean
}
